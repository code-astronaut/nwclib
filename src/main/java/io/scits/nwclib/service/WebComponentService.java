package io.scits.nwclib.service;

import io.scits.nwclib.controller.dto.WebComponentDto;

import java.util.List;

public interface WebComponentService {

    WebComponentDto createWebComponent(WebComponentDto createData);

    WebComponentDto getWebComponent(Long id);

    List<WebComponentDto> getWebComponents(Integer pageNumber, Integer pageSize);

    WebComponentDto updateWebComponent(Long id, WebComponentDto updateData);

    WebComponentDto patchWebComponent(Long id, WebComponentDto patchData);

    void delete(Long id);

}
