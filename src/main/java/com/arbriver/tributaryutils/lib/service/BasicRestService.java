package com.arbriver.tributaryutils.lib.service;

import lombok.NonNull;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import java.text.MessageFormat;

@Service
public class BasicRestService {
    protected final Environment environment;
    protected WebClient webClient;


    public BasicRestService(WebClient webClient, Environment environment) {
        this.webClient = webClient;
        this.environment = environment;
    }

    public WebClient.RequestHeadersSpec<?> getBasicTemplatedRequest(@NonNull String uriTemplate, String ... strings) {
        String mainURI = MessageFormat.format(uriTemplate, (Object[]) strings);
        return webClient.get()
                .uri(mainURI);
    }

    public WebClient.RequestHeadersSpec<?> getBasicRequest(@NonNull String uri) {
        return webClient.get()
                .uri(uri);
    }

    public WebClient.RequestHeadersSpec<?> postBasicTemplatedRequest(@NonNull String uri, Object body, String... strings) {
        return webClient.post()
                .uri(MessageFormat.format(uri, (Object[]) strings))
                .bodyValue(body);
    }
}
