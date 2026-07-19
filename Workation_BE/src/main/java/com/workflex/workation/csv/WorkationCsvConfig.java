package com.workflex.workation.csv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class WorkationCsvConfig {

    @Bean
    public Resource workationsCsvResource(ResourceLoader resourceLoader,
                                           @Value("${workation.import.csv-path}") String csvPath) {
        return resourceLoader.getResource(csvPath);
    }
}
