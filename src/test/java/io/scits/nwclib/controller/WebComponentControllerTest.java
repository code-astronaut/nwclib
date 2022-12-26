package io.scits.nwclib.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.scits.nwclib.controller.dto.WebComponentDto;
import io.scits.nwclib.service.WebComponentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WebComponentController.class)
public class WebComponentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WebComponentService webComponentService;

    @Test
    void createWebComponent_mustWork() throws Exception {

        WebComponentDto webComponent = WebComponentDto.builder()
                .name("Button1")
                .description("A simple button")
                .script("script")
                .image("image")
                .build();

        ResultActions response = mockMvc.perform(post("/webcomponents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(webComponent)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(webComponent.getName())))
                .andExpect(jsonPath("$.description",
                        is(webComponent.getDescription())))
                .andExpect(jsonPath("$.script",
                        is(webComponent.getScript())))
                .andExpect(jsonPath("$.image",
                        is(webComponent.getImage())));

        verify(webComponentService, times(1)).createWebComponent(webComponent);

    }
}
