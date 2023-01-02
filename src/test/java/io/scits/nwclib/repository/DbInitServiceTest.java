package io.scits.nwclib.repository;


import io.scits.nwclib.model.WebComponentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@ActiveProfiles("local")
@SpringBootTest
public class DbInitServiceTest {

    @Autowired
    private WebComponentRepository webComponentRepository;

    @Test
    public void givenDbInitServicePresentThenShouldInsertRecordOnStart() {

        List<WebComponentEntity> webComponents = webComponentRepository.findAll();
        assertEquals("record count is not matching", 3, webComponents.size());

        WebComponentEntity webComponent = webComponents.get(0);
        assertEquals("Component name", "button1", webComponent.getName());
        assertEquals("Component description", "A simple button", webComponent.getDescription());
    }
}
