package com.arbriver.tributaryutils.lib.reactor.model;

import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import reactor.core.publisher.Flux;

public interface ReactiveRetriever {
    Flux<MatchUpdate> startRetrieving();
}
