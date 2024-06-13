package com.arbriver.tributaryutils.app;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@AutoConfiguration
@EnableMongoRepositories(basePackages = "com.arbriver")
@ComponentScan("com.arbriver")
public class SharedConfigurationReference {
    @Bean
    public WebClient webClient() {
        return WebClient.builder().codecs(configurer ->
                configurer.defaultCodecs().maxInMemorySize(Integer.MAX_VALUE)).build();
    }
}
