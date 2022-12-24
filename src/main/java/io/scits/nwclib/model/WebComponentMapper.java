package io.scits.nwclib.model;

import io.scits.nwclib.controller.dto.WebComponentDto;
import org.mapstruct.Mapper;

@Mapper
public interface WebComponentMapper {
    WebComponentDto toDto(WebComponentEntity webComponentEntity);
}
