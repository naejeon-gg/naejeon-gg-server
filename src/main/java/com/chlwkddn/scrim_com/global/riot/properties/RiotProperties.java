package com.chlwkddn.scrim_com.global.riot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "riot.api")
@Getter
@Setter
public class RiotProperties {
    private String key;
}
