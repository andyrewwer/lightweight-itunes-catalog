package com.andrew.lightweightitunescatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CatalogConfiguration {

    private final CatalogConfigurationProperties catalogConfigurationProperties;

    @Autowired
    public CatalogConfiguration(CatalogConfigurationProperties catalogConfigurationProperties) {
        this.catalogConfigurationProperties = catalogConfigurationProperties;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplateBuilder().rootUri(catalogConfigurationProperties.getApiUrl()).build();
        for (HttpMessageConverter<?> myConverter : restTemplate.getMessageConverters ()) {
            if (myConverter instanceof MappingJackson2HttpMessageConverter) {
                List<MediaType> myMediaTypes = new ArrayList<>(myConverter.getSupportedMediaTypes());
                myMediaTypes.add(MediaType.parseMediaType ("text/javascript; charset=utf-8"));
                ((MappingJackson2HttpMessageConverter) myConverter).setSupportedMediaTypes(myMediaTypes);
            }
        }
        return restTemplate;
    }

}
