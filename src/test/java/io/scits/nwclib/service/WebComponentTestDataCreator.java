package io.scits.nwclib.service;

import io.scits.nwclib.controller.dto.WebComponentDto;
import io.scits.nwclib.model.WebComponentEntity;
import io.scits.nwclib.model.WebComponentMapper;
import io.scits.nwclib.repository.WebComponentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class WebComponentTestDataCreator implements TestDataCreator<WebComponentEntity, WebComponentDto> {
    private final WebComponentRepository webComponentRepository;
    private final WebComponentMapper webComponentMapper;

    public WebComponentDto getWebComponentDtoWithAllFields() {
        return WebComponentDto.builder()
                .name(RandomStringUtils.randomAlphabetic(20))
                .description("This is a cool WebComponent")
                .script(RandomStringUtils.randomAlphabetic(1000))
                .image(RandomStringUtils.randomAlphabetic(1000))
                .build();
    }

    public WebComponentEntity createWebComponentEntityWithAllFields() {
        return WebComponentEntity.builder()
                .name(RandomStringUtils.randomAlphabetic(20))
                .description("This is a cool WebComponent")
                .script(RandomStringUtils.randomAlphabetic(1000))
                .image(RandomStringUtils.randomAlphabetic(1000))
                .build();
    }

    public void saveWebComponentEntitiesWithAllFields(int amount) {
        IntStream.range(0, amount).forEach(number -> getSavedWebComponentEntityWithAllFields());
    }

    public WebComponentEntity getSavedWebComponentEntityWithAllFields() {
        return save(createWebComponentEntityWithAllFields());
    }

    @Override
    public WebComponentEntity save(WebComponentEntity entity) {
        return webComponentRepository.save(entity);
    }

    @Override
    public Long saveDomainModel(WebComponentDto dto) {
        return webComponentRepository.save(webComponentMapper.toEntity(dto)).getId();
    }
}
