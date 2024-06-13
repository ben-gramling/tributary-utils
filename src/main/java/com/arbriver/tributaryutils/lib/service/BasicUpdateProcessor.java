package com.arbriver.tributaryutils.lib.service;

import com.arbriver.tributaryutils.lib.dal.MatchRepository;
import com.arbriver.tributaryutils.lib.dal.PositionRepository;
import com.arbriver.tributaryutils.lib.model.*;
import org.springframework.stereotype.Component;

@Component
public class BasicUpdateProcessor implements UpdateProcessor {
    private final MatchRepository matchRepository;
    private final PositionRepository positionRepository;

    public BasicUpdateProcessor(MatchRepository matchRepository, PositionRepository positionRepository) {
        this.matchRepository = matchRepository;
        this.positionRepository = positionRepository;
    }

    public void processUpdate(MatchUpdate update) {
        //for now we are just saving the update to mongodb.
        //in the future we will check to see if it is a genuine update and we will send to stream
        matchRepository.save(update.getMatch());
        positionRepository.saveAll(update.getPositions());
    }
}
