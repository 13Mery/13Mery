package com.maria.adoption.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("adoption.config")
public class AdoptionConfigurationProperties {
    private boolean seederEnabled = false;
}
