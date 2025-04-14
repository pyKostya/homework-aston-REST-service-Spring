package com.pykost.rest.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        JpaConfiguration.class,
        RestConfiguration.class
})
@ComponentScan(basePackages = "com.pykost.rest")
@Configuration
public class AppConfiguration {
}
