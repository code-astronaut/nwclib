package io.scits.nwclib.service;

import io.scits.nwclib.controller.dto.WebComponentDto;
import io.scits.nwclib.exception.WebComponentNotFoundException;
import io.scits.nwclib.model.WebComponentEntity;
import io.scits.nwclib.model.WebComponentImageEntity;
import io.scits.nwclib.model.WebComponentMapper;
import io.scits.nwclib.repository.WebComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WebComponentServiceImpl implements WebComponentService {
    private final WebComponentRepository webComponentRepository;
    private final WebComponentMapper webComponentMapper;

    @Override
    public WebComponentDto createWebComponent(WebComponentDto createData, MultipartFile multipartFile) {
        WebComponentEntity newWebComponent = WebComponentEntity
                .builder()
                .title(createData.getTitle())
                .description(createData.getDescription())
                .script(createData.getScript())
                .build();

        saveImage(newWebComponent, multipartFile);

        return webComponentMapper.toDto(webComponentRepository.save(newWebComponent));
    }

    private void saveImage(WebComponentEntity newWebComponent,
                            MultipartFile multipartFile) {
        createWebComponentImageFromMultipartFile(newWebComponent, multipartFile);
    }

    private void createWebComponentImageFromMultipartFile(WebComponentEntity newWebComponent,
                                                          MultipartFile multipartFile) {
        try {
            WebComponentImageEntity.builder()
                    .imageData(multipartFile.getBytes())
                    .webComponentEntity(newWebComponent)
                    .build();
        } catch (IOException e) {
            // create better exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebComponentDto getWebComponent(Long id) {
        return webComponentMapper.toDto(webComponentRepository.findById(id).orElseThrow(() -> createWebComponentNotFoundException(id)));
    }

    @Override
    public List<WebComponentDto> getWebComponents(Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<WebComponentEntity> pagedResults = webComponentRepository.findAll(paging);
        if (pagedResults.hasContent()) {
            return pagedResults.getContent()
                    .stream()
                    .map(webComponentMapper::toDto)
                    .toList();
        }
        return List.of();
    }

    @Override
    public WebComponentDto updateWebComponent(Long id, WebComponentDto updateData) {
        WebComponentEntity webComponentEntity = webComponentRepository.findById(id).orElseThrow(() -> createWebComponentNotFoundException(id));

        webComponentEntity.setDescription(updateData.getDescription());
        webComponentEntity.setScript(updateData.getScript());
        webComponentEntity.setTitle(updateData.getTitle());

        return webComponentMapper.toDto(webComponentRepository.save(webComponentEntity));
    }

    @Override
    public WebComponentDto patchWebComponent(Long id, WebComponentDto patchData) {
        WebComponentEntity webComponentEntity = webComponentRepository.findById(id).orElseThrow(() -> createWebComponentNotFoundException(id));

        if (patchData.getScript() != null) {
            webComponentEntity.setScript(patchData.getScript());
        }

        if (patchData.getDescription() != null) {
            webComponentEntity.setDescription(patchData.getDescription());
        }

        if (patchData.getTitle() != null) {
            webComponentEntity.setTitle(patchData.getTitle());
        }

        return webComponentMapper.toDto(webComponentRepository.save(webComponentEntity));
    }

    @Override
    public void delete(Long id) {
        webComponentRepository.deleteById(id);
    }

    private WebComponentNotFoundException createWebComponentNotFoundException(Long id) {
        return new WebComponentNotFoundException(format("WebComponent with id %s not found!", id));
    }
}
