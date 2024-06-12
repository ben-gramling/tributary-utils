package com.arbriver.tributaryutils;

import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import com.arbriver.tributaryutils.lib.model.RetrievalService;
import com.arbriver.tributaryutils.lib.service.MatchStateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultContainer {
    private final Logger _logger = LoggerFactory.getLogger(this.getClass());
    private final MatchStateMachine matchStateMachine;
    private final RetrievalService retrievalService;

    public DefaultContainer(MatchStateMachine matchStateMachine, RetrievalService retrievalService) {
        this.matchStateMachine = matchStateMachine;
        this.retrievalService = retrievalService;
    }

    public void start(String[] args) {
        startProcessing();
    }

    private void startProcessing() {
        while(true) {
            List<MatchUpdate> updates = retrievalService.getUpdates();

            for(MatchUpdate update : updates) {
                matchStateMachine.processUpdate(update);
            }
        }
    }
}
