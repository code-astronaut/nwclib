package io.scits.nwclib.repository;

import io.scits.nwclib.model.WebComponentEntity;
import io.scits.nwclib.service.WebComponentTestDataCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
class WebComponentRepositoryIT {
    @Autowired
    private WebComponentRepository webComponentRepository;

    @Autowired
    private WebComponentTestDataCreator webComponentTestDataCreator;

    @BeforeEach
    void setupBeforeEach() {
        webComponentRepository.deleteAll();
    }

    @AfterEach
    void setupAfterEach() {
        webComponentRepository.deleteAll();
    }

    @Test
    void findByName_mustWork() {
        WebComponentEntity webComponentShouldBeFound = webComponentTestDataCreator.getSavedWebComponentEntityWithAllFields();
        webComponentTestDataCreator.getSavedWebComponentEntityWithAllFields();

        Optional<WebComponentEntity> loadedWebComponentByName = webComponentRepository.findByName(webComponentShouldBeFound.getName());

        assertThat(loadedWebComponentByName).isPresent();
        assertThat(loadedWebComponentByName.get().getName()).isEqualTo(webComponentShouldBeFound.getName());
    }
}
