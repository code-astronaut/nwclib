package io.scits.nwclib.service;

import io.scits.nwclib.repository.WebComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebComponentTestDataResetter implements TestDataResetter {
    private final WebComponentRepository webComponentRepository;

    @Override
    public void resetData() {
        webComponentRepository.deleteAll();
    }
}
