package ru.FSPO.AIS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class UploadConfig {
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(10000000);
        return commonsMultipartResolver;
    }
}