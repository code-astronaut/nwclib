package io.scits.nwclib.service;

import io.scits.nwclib.controller.dto.WebComponentDto;
import io.scits.nwclib.exception.WebComponentNameNotAvailableException;
import io.scits.nwclib.exception.WebComponentNotFoundException;
import io.scits.nwclib.model.WebComponentEntity;
import io.scits.nwclib.model.WebComponentMapper;
import io.scits.nwclib.repository.WebComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

/*@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WebComponentServiceImpl implements WebComponentService {
    private final WebComponentRepository webComponentRepository;
    private final WebComponentMapper webComponentMapper;

    @Override
    @Transactional
    public WebComponentDto createWebComponent(WebComponentDto createData) {
        WebComponentEntity newWebComponent = WebComponentEntity
                .builder()
                .name(createData.getName())
                .description(createData.getDescription())
                .script(createData.getScript())
                .image(createData.getImage())
                .build();

        boolean webComponentExistsByName = webComponentRepository.existsByName(createData.getName());
        if (webComponentExistsByName) {
            throw createWebComponentNameNotAvailableException(createData.getName());
        }

        return webComponentMapper.toDto(webComponentRepository.save(newWebComponent));
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
    @Transactional
    public WebComponentDto updateWebComponent(Long id, WebComponentDto updateData) {
        WebComponentEntity webComponentEntity = webComponentRepository.findById(id).orElseThrow(() -> createWebComponentNotFoundException(id));

        webComponentEntity.setDescription(updateData.getDescription());
        webComponentEntity.setScript(updateData.getScript());
        webComponentEntity.setName(updateData.getName());

        return webComponentMapper.toDto(webComponentRepository.save(webComponentEntity));
    }

    @Override
    @Transactional
    public WebComponentDto patchWebComponent(Long id, WebComponentDto patchData) {
        WebComponentEntity webComponentEntity = webComponentRepository.findById(id).orElseThrow(() -> createWebComponentNotFoundException(id));

        if (patchData.getScript() != null) {
            webComponentEntity.setScript(patchData.getScript());
        }

        if (patchData.getDescription() != null) {
            webComponentEntity.setDescription(patchData.getDescription());
        }

        if (patchData.getImage() != null) {
            webComponentEntity.setImage(patchData.getImage());
        }

        return webComponentMapper.toDto(webComponentRepository.save(webComponentEntity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        webComponentRepository.deleteById(id);
    }

    private WebComponentNotFoundException createWebComponentNotFoundException(Long id) {
        return new WebComponentNotFoundException(format("WebComponent with id %s not found!", id));
    }

    private WebComponentNameNotAvailableException createWebComponentNameNotAvailableException(String webComponentName) {
        return new WebComponentNameNotAvailableException(format("WebComponent with name %s is not available!", webComponentName));
    }
}*/
