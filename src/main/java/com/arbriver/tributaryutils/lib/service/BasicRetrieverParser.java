package com.arbriver.tributaryutils.lib.service;

import com.arbriver.tributaryutils.lib.model.EventIDMapping;
import com.arbriver.tributaryutils.lib.model.MatchMarketsMapping;
import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import com.fasterxml.jackson.databind.JsonNode;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public abstract class BasicRetrieverParser {
    protected Environment env;

    @Autowired
    public BasicRetrieverParser(Environment env) {
        this.env = env;
    }

    public abstract Flux<EventIDMapping> parseEventResponse(Publisher<JsonNode> jsonResponse);
    public abstract Flux<MatchUpdate> parseMarketsResponse(Publisher<MatchMarketsMapping> jsonResponse);
}
