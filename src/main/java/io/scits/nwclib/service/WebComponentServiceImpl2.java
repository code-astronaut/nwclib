package io.scits.nwclib.service;

import io.scits.nwclib.controller.dto.WebComponentDto;
import io.scits.nwclib.model.WebComponentEntity;
import io.scits.nwclib.repository.WebComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WebComponentServiceImpl2 implements WebComponentService {

    private final WebComponentRepository webComponentRepository;

    @Override
    public WebComponentDto createWebComponent(WebComponentDto createData) {
        return null;
    }

    @Override
    public WebComponentDto getWebComponent(Long id) {
        return null;
    }

    @Override
    public List<WebComponentDto> getWebComponents(Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<WebComponentEntity> pagedResults = webComponentRepository.findAll(paging);
        if (pagedResults.hasContent()) {
            return pagedResults.getContent()
                    .stream()
                    .map(entity -> WebComponentDto.builder()
                            .name(entity.getName())
                            .description(entity.getDescription())
                            .script(entity.getScript().getId().toString())
                            .image(entity.getThumbnail().getId().toString())
                            .build())
                    .toList();
        }
        return List.of();
    }

    @Override
    public WebComponentDto updateWebComponent(Long id, WebComponentDto updateData) {
        return null;
    }

    @Override
    public WebComponentDto patchWebComponent(Long id, WebComponentDto patchData) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
