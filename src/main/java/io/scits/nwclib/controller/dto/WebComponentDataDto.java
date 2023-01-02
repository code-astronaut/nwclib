package io.scits.nwclib.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebComponentDataDto {

    private String name;

    private String description;

    private String scriptPath;

    private String imagePath;
}
