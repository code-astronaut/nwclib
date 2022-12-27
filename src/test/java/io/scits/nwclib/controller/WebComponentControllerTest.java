package io.scits.nwclib.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.scits.nwclib.controller.dto.WebComponentDto;
import io.scits.nwclib.service.WebComponentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class WebComponentControllerTest {
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

        when(webComponentService.createWebComponent(any(WebComponentDto.class))).thenReturn(webComponent);

        String request = objectMapper.writeValueAsString(webComponent);

        mockMvc.perform(post("/webcomponents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(webComponent.getName()))
                .andExpect(jsonPath("$.description").value(webComponent.getDescription()))
                .andExpect(jsonPath("$.script").value(webComponent.getScript()))
                .andExpect(jsonPath("$.image").value(webComponent.getImage()));

        verify(webComponentService, times(1)).createWebComponent(webComponent);
    }

    @Test
    void getWebComponent_mustWork() throws Exception {
        Long id = 1L;

        WebComponentDto webComponent = WebComponentDto.builder()
                .name("Button1")
                .description("A simple button")
                .script("script")
                .image("image")
                .build();

        when(webComponentService.getWebComponent(any(Long.class))).thenReturn(webComponent);

        mockMvc.perform(get("/webcomponents/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(webComponent.getName()))
                .andExpect(jsonPath("$.description").value(webComponent.getDescription()))
                .andExpect(jsonPath("$.script").value(webComponent.getScript()))
                .andExpect(jsonPath("$.image").value(webComponent.getImage()));

        verify(webComponentService, times(1)).getWebComponent(id);
    }

    @Test
    void getWebComponents_mustWork() throws Exception {
        WebComponentDto webComponent = WebComponentDto.builder()
                .name("Button1")
                .description("A simple button")
                .script("script")
                .image("image")
                .build();

        List<WebComponentDto> webComponentDtoList = List.of(webComponent, webComponent, webComponent, webComponent, webComponent);

        when(webComponentService.getWebComponents(any(Integer.class), any(Integer.class)))
                .thenReturn(webComponentDtoList);

        mockMvc.perform(get("/webcomponents")
                        .param("pageNumber", "0")
                        .param("pageSize", "5"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(webComponentService, times(1)).getWebComponents(0, 5);
    }
}
