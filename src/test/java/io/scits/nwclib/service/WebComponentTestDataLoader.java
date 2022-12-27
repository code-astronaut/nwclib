package io.scits.nwclib.service;

import io.scits.nwclib.exception.WebComponentNotFoundException;
import io.scits.nwclib.model.WebComponentEntity;
import io.scits.nwclib.repository.WebComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class WebComponentTestDataLoader {
    private final WebComponentRepository webComponentRepository;

    public WebComponentEntity findById(Long id) {
        return webComponentRepository.findById(id)
                .orElseThrow(() -> new WebComponentNotFoundException(format("WebComponent with id %s not found", id)));
    }
    @Transactional
    public WebComponentEntity findByName(String webComponentName) {
        return webComponentRepository.findByName(webComponentName)
                .orElseThrow(() -> new WebComponentNotFoundException(format("WebComponent with name %s not found", webComponentName)));
    }

    public List<WebComponentEntity> findAll() {
        return webComponentRepository.findAll();
    }
}
