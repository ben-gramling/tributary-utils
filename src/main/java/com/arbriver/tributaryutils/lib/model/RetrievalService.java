package com.arbriver.tributaryutils.lib.model;

import java.util.List;

public interface RetrievalService {
    public void send(MatchUpdate update);
    public void setUpdateProcessor(UpdateProcessor updateProcessor);
    public void start();
}
