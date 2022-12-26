package io.scits.nwclib.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.scits.nwclib.controller.dto.WebComponentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        WebComponentDto webComponent = WebComponentDto.builder().name("Button1").description("A simple button").script("script").image("image").build();

        String request = objectMapper.writeValueAsString(webComponent);
        mockMvc.perform(post("/webcomponents").contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(webComponent.getName()))
                .andExpect(jsonPath("$.description").value(webComponent.getDescription()))
                .andExpect(jsonPath("$.script").value(webComponent.getScript()))
                .andExpect(jsonPath("$.image").value(webComponent.getImage()));
    }
}
