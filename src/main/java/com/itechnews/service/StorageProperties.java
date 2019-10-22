package com.itechnews.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
    https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/html/
    configuration-metadata.html#configuration-metadata-annotation-processor
*/
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageProperties {
    private String location;
}
