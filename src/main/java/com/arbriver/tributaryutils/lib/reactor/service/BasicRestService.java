package com.arbriver.tributaryutils.lib.reactor.service;

import com.arbriver.tributaryutils.lib.model.EventIDMapping;
import com.arbriver.tributaryutils.lib.model.MatchMarketsMapping;
import com.google.gson.JsonElement;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.Objects;

@Service
public abstract class BasicRestService {
    private final Logger _logger = LoggerFactory.getLogger(this.getClass());
    protected final String PROP_PREFIX = "retriever.request.";
    protected final ReactiveRestClient restService;
    protected final Environment env;

    public BasicRestService(ReactiveRestClient reactiveRestClient, Environment env) {
        this.restService = reactiveRestClient;
        this.env = env;
    }

    //IMPLEMENT THIS METHOD IN SUBCLASS
    public abstract Flux<JsonElement> getEventStream();

    //IMPLEMENT THIS METHOD IN SUBCLASS
    public abstract Flux<MatchMarketsMapping> getMarketsStream(Publisher<EventIDMapping> eventStream);

    protected String buildRequestTemplate(String propertyName, String ... strings) {
        String requestTemplate = Objects.requireNonNull(env.getProperty(PROP_PREFIX + propertyName + ".template"));
        return MessageFormatter.format(requestTemplate, strings).toString();
    }
}
