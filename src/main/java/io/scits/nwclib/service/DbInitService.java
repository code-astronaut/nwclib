package io.scits.nwclib.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.scits.nwclib.config.NwclibProperties;
import io.scits.nwclib.controller.dto.WebComponentDataDto;
import io.scits.nwclib.model.ScriptEntity;
import io.scits.nwclib.model.ThumbnailEntity;
import io.scits.nwclib.model.WebComponentEntity;
import io.scits.nwclib.repository.WebComponentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class DbInitService {

    private final NwclibProperties nwclibProperties;
    private final ObjectReader reader = new ObjectMapper().readerForListOf(WebComponentDataDto.class);
    private final WebComponentRepository webComponentRepository;

    @PostConstruct
    private void initDb() {
        try {
            List<Resource> dataResources = Arrays.asList(new PathMatchingResourcePatternResolver().getResources(nwclibProperties.getWebComponentDataLocationPattern()));

            if (dataResources.size() == 0) {
                log.warn("No web component data found for configured location pattern: {}", nwclibProperties.getWebComponentDataLocationPattern());
            }

            dataResources.forEach(resource -> {
                List<WebComponentDataDto> webComponentData = getContent(resource);
                webComponentData.forEach(data -> {
                    String scriptContent = getScript(data.getScriptPath());
                    ScriptEntity script = ScriptEntity.builder().content(scriptContent).build();
                    byte[] image = getThumbnail(data.getImagePath());
                    ThumbnailEntity thumbnail = ThumbnailEntity.builder().content(image).build();
                    webComponentRepository.save(WebComponentEntity.builder()
                            .name(data.getName())
                            .description(data.getDescription())
                            .script(script)
                            .thumbnail(thumbnail)
                            .build());
                });
            });

        } catch (IOException e){
            log.error("Failed to get web component data from location pattern: {}", nwclibProperties.getWebComponentDataLocationPattern());
            e.printStackTrace();
        }
    }

    private List<WebComponentDataDto> getContent(Resource resource) {
        try {
            return reader.readValue(resource.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getScript(String script) {
        try {
            return new String(new PathMatchingResourcePatternResolver()
                    .getResource(script)
                    .getInputStream()
                    .readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Failed to read file! resource: {}", script );
            e.printStackTrace();
            return null;
        }
    }

    private byte[] getThumbnail(String image) {
        try {
            return new PathMatchingResourcePatternResolver()
                    .getResource(image)
                    .getInputStream()
                    .readAllBytes();
        } catch (IOException e) {
            log.error("Failed to read file! resource: {}", image);
            e.printStackTrace();
            return null;
        }
    }
}
