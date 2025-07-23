package com.rcs.common.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt的配置绑定类
 */

@Data
@Component
@ConfigurationProperties(prefix = "jwt.utils")
public class JwtProperties {
    private String signKey;
    private Long expire;
}
