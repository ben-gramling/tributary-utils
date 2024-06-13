package com.arbriver.tributaryutils.lib.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import java.text.MessageFormat;

@Service
public class BasicRestService {
    protected WebClient webClient;

    public BasicRestService(WebClient webClient) {
        this.webClient = webClient;
    }

    public WebClient.RequestHeadersSpec<?> getBasicTemplatedRequest(@NonNull String uriTemplate, String ... strings) {
        return webClient.get()
                .uri(MessageFormat.format(uriTemplate, (Object[]) strings));
    }

    public WebClient.RequestHeadersSpec<?> getBasicRequest(@NonNull String uri) {
        return webClient.get()
                .uri(uri);
    }


}
