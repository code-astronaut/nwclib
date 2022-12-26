package io.scits.nwclib.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.scits.nwclib.controller.dto.WebComponentDto;
import io.scits.nwclib.service.WebComponentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WebComponentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createWebComponent_mustWork() throws Exception {

        WebComponentDto webComponent = WebComponentDto.builder()
                .name("Button1")
                .description("A simple button")
                .script("script")
                .image("image")
                .build();

        mockMvc.perform(post("/webcomponents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(webComponent)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(webComponent.getName()))
                .andExpect(jsonPath("$.description").value(webComponent.getDescription()))
                .andExpect(jsonPath("$.script").value(webComponent.getScript()))
                .andExpect(jsonPath("$.image").value(webComponent.getImage()));
    }
}
