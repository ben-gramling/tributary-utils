package com.arbriver.tributaryutils.lib.reactor.service;

import com.arbriver.tributaryutils.lib.model.EventIDMapping;
import com.arbriver.tributaryutils.lib.model.MatchMarketsMapping;
import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import com.google.gson.JsonElement;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public abstract class BasicRetrieverParser {
    public abstract Flux<EventIDMapping> parseEventResponse(Publisher<JsonElement> jsonResponse);
    public abstract Flux<MatchUpdate> parseMarketsResponse(Publisher<MatchMarketsMapping> jsonResponse);
}
