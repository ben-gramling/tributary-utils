package com.arbriver.tributaryutils;

import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import com.arbriver.tributaryutils.lib.model.service.ReactiveProcessor;
import com.arbriver.tributaryutils.lib.model.service.ReactiveRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ReactiveContainer {
    private final Logger _logger = LoggerFactory.getLogger(this.getClass());
    private final ReactiveProcessor updateProcessor;
    private final ReactiveRetriever retrievalService;

    public ReactiveContainer(
            @Qualifier("processor") ReactiveProcessor updateProcessor,
            @Qualifier("retriever") ReactiveRetriever retrievalService) {
        this.updateProcessor = updateProcessor;
        this.retrievalService = retrievalService;
    }

    public void start() {
        Flux<MatchUpdate> updates = retrievalService.startRetrieving();
        Flux<Object> saveResult = updateProcessor.processUpdates(updates);
        saveResult.doOnComplete(() -> {
            _logger.info("Completed execution. Shutting down.");
            System.exit(0);
        }).subscribe();
    }
}
