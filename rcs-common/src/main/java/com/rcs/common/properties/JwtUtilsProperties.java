package com.rcs.common.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.utils")
public class JwtUtilsProperties {
    private String signKey;
    private Long expire;
}
