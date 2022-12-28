package io.scits.nwclib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NwclibApplication {

	public static void main(String[] args) {
		SpringApplication.run(NwclibApplication.class, args);
	}

}
