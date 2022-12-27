package io.scits.nwclib.service;

import io.scits.nwclib.controller.dto.WebComponentDto;
import io.scits.nwclib.exception.WebComponentNameNotAvailableException;
import io.scits.nwclib.exception.WebComponentNotFoundException;
import io.scits.nwclib.model.WebComponentEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles("local")
class WebComponentServiceIT {

    @Autowired
    private WebComponentService webComponentService;

    @Autowired
    private WebComponentTestDataCreator webComponentTestDataCreator;

    @Autowired
    private WebComponentTestDataLoader webComponentTestDataLoader;

    @Autowired
    private WebComponentTestDataResetter webComponentTestDataResetter;

    @BeforeEach
    void resetDataBefore() {
        webComponentTestDataResetter.resetData();
    }

    @AfterEach
    void resetDataAfter() {
        webComponentTestDataResetter.resetData();
    }

    @Test
    void createWebComponent_mustWork() {
        WebComponentDto webComponentDto = webComponentTestDataCreator.getWebComponentDtoWithAllFields();

        WebComponentDto webComponentAfterCreation = webComponentService.createWebComponent(webComponentDto);

        WebComponentEntity webComponentEntity = webComponentTestDataLoader.findByName(webComponentAfterCreation.getName());

        assertThat(webComponentEntity.getId()).isNotNull();
        assertThat(webComponentEntity.getName()).isEqualTo(webComponentDto.getName());
        assertThat(webComponentEntity.getDescription()).isEqualTo(webComponentDto.getDescription());
        assertThat(webComponentEntity.getScript()).isEqualTo(webComponentDto.getScript());
        assertThat(webComponentEntity.getImage()).isEqualTo(webComponentDto.getImage());
    }

    @Test
    void createWebComponent_shouldThrow_webComponentNameNotAvailableException() {
        WebComponentDto webComponentDto = webComponentTestDataCreator.getWebComponentDtoWithAllFields();

        webComponentService.createWebComponent(webComponentDto);

        assertThatExceptionOfType(WebComponentNameNotAvailableException.class)
                .isThrownBy(() -> webComponentService.createWebComponent(webComponentDto));
    }

    @Test
    void getWebComponent_mustWork() {
        WebComponentEntity savedWebComponentEntityWithAllFields = webComponentTestDataCreator.getSavedWebComponentEntityWithAllFields();

        WebComponentDto loadedWebComponent = webComponentService.getWebComponent(savedWebComponentEntityWithAllFields.getId());

        assertThat(loadedWebComponent.getName()).isEqualTo(savedWebComponentEntityWithAllFields.getName());
        assertThat(loadedWebComponent.getDescription()).isEqualTo(savedWebComponentEntityWithAllFields.getDescription());
        assertThat(loadedWebComponent.getScript()).isEqualTo(savedWebComponentEntityWithAllFields.getScript());
        assertThat(loadedWebComponent.getImage()).isEqualTo(savedWebComponentEntityWithAllFields.getImage());
    }

    @Test
    void getWebComponent_shouldThrow_webComponentNotFoundException() {
        Long wrongId = 3L;
        assertThatExceptionOfType(WebComponentNotFoundException.class)
                .isThrownBy(() -> webComponentService.getWebComponent(wrongId));
    }

    private static Stream<Arguments> getWebComponents_params() {
        return Stream.of(
                Arguments.of(0, 5, 20, 5),
                Arguments.of(1, 5, 8, 3),
                Arguments.of(2, 5, 11, 1),
                Arguments.of(2, 5, 10, 0),
                Arguments.of(0, 10, 10, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("getWebComponents_params")
    void getWebComponents_mustWork(int pageNumber, int pageSize, int webComponentAmount, int expectedAmountInPage) {
        webComponentTestDataCreator.saveWebComponentEntitiesWithAllFields(webComponentAmount);

        List<WebComponentEntity> allWebComponents = webComponentTestDataLoader.findAll();
        assertThat(allWebComponents).hasSize(webComponentAmount);

        List<WebComponentDto> webComponents = webComponentService.getWebComponents(pageNumber, pageSize);

        assertThat(webComponents).hasSize(expectedAmountInPage);
    }

    @Test
    void updateWebComponent_mustWork() {
        WebComponentEntity webComponent = webComponentTestDataCreator.getSavedWebComponentEntityWithAllFields();

        WebComponentDto webComponentDto = WebComponentDto.builder()
                .script("newScipt")
                .description("newDescription")
                .image("newImage")
                .build();

        WebComponentDto updatedWebComponent = webComponentService.updateWebComponent(webComponent.getId(), webComponentDto);
        WebComponentEntity loadedUpdatedWebComponent = webComponentTestDataLoader.findById(webComponent.getId());

        assertThat(loadedUpdatedWebComponent.getId()).isEqualTo(webComponent.getId());
        assertThat(loadedUpdatedWebComponent.getScript()).isEqualTo(updatedWebComponent.getScript());
        assertThat(loadedUpdatedWebComponent.getDescription()).isEqualTo(updatedWebComponent.getDescription());
        assertThat(loadedUpdatedWebComponent.getImage()).isEqualTo(updatedWebComponent.getImage());
    }

    @Test
    void updateWebComponent_shouldThrow_webComponentNotFoundException() {
        Long wrongId = 3L;
        WebComponentDto webComponentDto = WebComponentDto.builder().build();
        assertThatExceptionOfType(WebComponentNotFoundException.class)
                .isThrownBy(() -> webComponentService.updateWebComponent(wrongId, webComponentDto));
    }

    private static Stream<Arguments> patchWebComponent_params() {
        String script = RandomStringUtils.randomAlphabetic(1000);
        String image = RandomStringUtils.randomAlphabetic(1000);
        String description = RandomStringUtils.randomAlphabetic(1000);
        return Stream.of(
                Arguments.of(WebComponentDto.builder()
                        .script(script)
                        .build(), script, null, null),
                Arguments.of(WebComponentDto.builder()
                        .description(description)
                        .build(), null, description, null),
                Arguments.of(WebComponentDto.builder()
                        .image(image)
                        .build(), null, null, image)
        );
    }

    @ParameterizedTest
    @MethodSource("patchWebComponent_params")
    void patchWebComponent_mustWork(WebComponentDto webComponentDto, String script, String description, String image) {
        WebComponentEntity webComponentWhichShouldBePatched = webComponentTestDataCreator.getSavedWebComponentEntityWithAllFields();

        webComponentService.patchWebComponent(webComponentWhichShouldBePatched.getId(), webComponentDto);
        WebComponentEntity patchedWebComponent = webComponentTestDataLoader.findById(webComponentWhichShouldBePatched.getId());

        assertThat(patchedWebComponent.getId()).isEqualTo(webComponentWhichShouldBePatched.getId());

        if (script != null) {
            assertThat(patchedWebComponent.getScript()).isEqualTo(script);
        } else {
            assertThat(patchedWebComponent.getScript()).isEqualTo(webComponentWhichShouldBePatched.getScript());
        }

        if (description != null) {
            assertThat(patchedWebComponent.getDescription()).isEqualTo(description);
        } else {
            assertThat(patchedWebComponent.getDescription()).isEqualTo(webComponentWhichShouldBePatched.getDescription());
        }

        if (image != null) {
            assertThat(patchedWebComponent.getImage()).isEqualTo(image);
        } else {
            assertThat(patchedWebComponent.getImage()).isEqualTo(webComponentWhichShouldBePatched.getImage());
        }

    }

    @Test
    void patchWebComponent_shouldThrow_webComponentNotFoundException() {
        Long wrongId = 3L;
        WebComponentDto webComponentDto = WebComponentDto.builder().build();
        assertThatExceptionOfType(WebComponentNotFoundException.class)
                .isThrownBy(() -> webComponentService.patchWebComponent(wrongId, webComponentDto));
    }

    @Test
    void delete_mustWork() {
        WebComponentEntity webComponentWhichShouldBeDeleted = webComponentTestDataCreator.getSavedWebComponentEntityWithAllFields();
        webComponentTestDataCreator.getSavedWebComponentEntityWithAllFields();

        List<WebComponentEntity> webComponentsBeforeDeletion = webComponentTestDataLoader.findAll();
        assertThat(webComponentsBeforeDeletion).hasSize(2);

        webComponentService.delete(webComponentWhichShouldBeDeleted.getId());

        List<WebComponentEntity> webComponentsAfterDeletion = webComponentTestDataLoader.findAll();
        assertThat(webComponentsAfterDeletion).hasSize(1);
    }
}
