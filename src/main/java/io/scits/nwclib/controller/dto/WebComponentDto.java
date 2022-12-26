package io.scits.nwclib.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebComponentDto {

    private String name;

    private String description;

    private String script;

    private String image;
}
