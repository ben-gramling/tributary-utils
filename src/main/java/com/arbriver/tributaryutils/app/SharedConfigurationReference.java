package com.arbriver.tributaryutils.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AutoConfiguration
@EnableReactiveMongoRepositories(basePackages = "com.arbriver")
@ComponentScan("com.arbriver")
public class SharedConfigurationReference {
    private final Logger _logger = LoggerFactory.getLogger(this.getClass());

    @Bean("base")
    public WebClient webClient() {
        return WebClient.builder().codecs(configurer ->
                configurer.defaultCodecs().maxInMemorySize(Integer.MAX_VALUE))
                .defaultHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                .build();
    }

    @Bean("proxy")
    public WebClient proxyWebClient() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer ->{
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    Jackson2JsonDecoder dec = new Jackson2JsonDecoder(mapper, MimeTypeUtils.parseMimeType(MediaType.TEXT_PLAIN_VALUE));
                    dec.setMaxInMemorySize(Integer.MAX_VALUE);
                    configurer.customCodecs()
                            .decoder(dec);
                }).build())
                .filter(ExchangeFilterFunction.ofResponseProcessor(this::renderApiErrorResponse))
                .defaultHeader("Content-Type","application/json")
                .defaultCookie("Accept","application/json")
                .build();
    }

    private Mono<ClientResponse> renderApiErrorResponse(ClientResponse clientResponse) {
        if(clientResponse.statusCode().isSameCodeAs(HttpStatusCode.valueOf(422))){
            return Mono.error(new Exception("Proxy could not access the endpoint: " + clientResponse.request().getURI().getQuery()));
        }
        if(clientResponse.statusCode().isSameCodeAs(HttpStatusCode.valueOf(400))){
            return Mono.error(new Exception("Proxy could not process the request: " + clientResponse.request().getURI().getQuery()));
        }
        return Mono.just(clientResponse);
    }
}