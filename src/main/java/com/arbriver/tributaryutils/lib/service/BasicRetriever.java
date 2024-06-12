package com.arbriver.tributaryutils.lib.service;

import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import com.arbriver.tributaryutils.lib.model.RetrievalService;
import com.arbriver.tributaryutils.lib.model.UpdateProcessor;

public abstract class BasicRetriever implements RetrievalService {
    UpdateProcessor updateProcessor;

    @Override
    public void start() {

    }

    @Override
    public void send(MatchUpdate update) {
        updateProcessor.processUpdate(update);
    }

    @Override
    public void setUpdateProcessor(UpdateProcessor _updateProcessor) {
        updateProcessor = _updateProcessor;
    }
}
