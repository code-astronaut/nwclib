package io.scits.nwclib.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("nwclib")
public class NwclibProperties {

    private String webComponentsLocationPattern = "classpath:js/web-components/*.js";
}
