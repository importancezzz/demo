package com.random.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ConfigValue {

    @Value("${uri.random.endpoint}")
    String uriRandomEndpoint;
}
