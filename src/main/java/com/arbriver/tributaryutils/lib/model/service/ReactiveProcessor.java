package com.arbriver.tributaryutils.lib.model.service;

import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public interface ReactiveProcessor {
    Flux<Object> processUpdates(Publisher<MatchUpdate> update);
}
