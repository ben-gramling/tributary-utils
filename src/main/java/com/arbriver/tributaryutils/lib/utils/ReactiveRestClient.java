package com.arbriver.tributaryutils.lib.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonElement;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Service
public class ReactiveRestClient {
    private final Logger _logger = LoggerFactory.getLogger(this.getClass());
    protected final Environment environment;
    private final WebClient webClient;
    private final WebClient proxyWebClient;
    private final URI baseURI;


    public ReactiveRestClient(
            @Qualifier("base") WebClient webClient,
            @Qualifier("proxy") WebClient proxyWebClient,
            Environment environment) {
        this.webClient = webClient;
        this.environment = environment;
        this.proxyWebClient = proxyWebClient;
        String proxyStringBaseURL = Objects.requireNonNull(environment.getProperty("proxy.template"));
        try {
            baseURI = new URIBuilder(proxyStringBaseURL)
                    .addParameter("js_instructions", Objects.requireNonNull(environment.getProperty("proxy.jsinstructions")))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public WebClient getWebClient(boolean useProxy) {
        return useProxy ? proxyWebClient : webClient;
    }

    public Mono<JsonNode> getRequest(String uri) {
        return getRequest(uri, false);
    }

    public Mono<JsonNode> getRequest(String uri, boolean useProxy) {
        if(useProxy) {
            try {
                URIBuilder uriBuilder = new URIBuilder(baseURI);
                URI proxyURI = uriBuilder.addParameter("url", uri).build();
                return proxyWebClient.get().uri(proxyURI).retrieve().bodyToMono(JsonNode.class).map(node -> node.get(0));
            } catch (URISyntaxException e) {
                _logger.error("Error parsing proxy uri: {}", uri, e);
                return Mono.error(e);
            }
        } else {
            return webClient.get().uri(uri).retrieve().bodyToMono(JsonNode.class);
        }
    }

    public Mono<JsonNode> getRequestWithClient(WebClient webClient, String uri) {
        return webClient.get().uri(uri).retrieve().bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> postRequest(String uri, Object body) {
        return postRequest(uri, body, false);
    }

    public Mono<JsonNode> postRequest(String uri, Object body, boolean useProxy) {
        if(useProxy) {
            try {
                URIBuilder uriBuilder = new URIBuilder(baseURI);
                URI proxyURI = uriBuilder.addParameter("url", uri).build();
                return proxyWebClient.post()
                        .uri(proxyURI)
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(JsonNode.class)
                        .map(node -> node.get(0));
            } catch (URISyntaxException e) {
                _logger.error("Error parsing proxy uri: {}", uri, e);
                return Mono.error(e);
            }
        } else {
            return webClient.post()
                    .uri(uri)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(JsonNode.class);
        }
    }

    public Mono<JsonElement> postRequestWithClient(WebClient webClient, String uri, Object body) {
        return webClient.post().uri(uri).bodyValue(body).retrieve().bodyToMono(JsonElement.class);
    }

}
