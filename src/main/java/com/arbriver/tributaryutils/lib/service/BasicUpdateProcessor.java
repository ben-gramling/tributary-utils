package com.arbriver.tributaryutils.lib.service;

import com.arbriver.tributaryutils.lib.dal.MatchRepository;
import com.arbriver.tributaryutils.lib.dal.PropPositionRepository;
import com.arbriver.tributaryutils.lib.dal.ResultPositionRepository;
import com.arbriver.tributaryutils.lib.model.*;
import org.springframework.stereotype.Component;

@Component
public class BasicUpdateProcessor implements UpdateProcessor {
    private final MatchRepository matchRepository;
    private final PropPositionRepository propPositionRepository;
    private final ResultPositionRepository resultPositionRepository;

    public BasicUpdateProcessor(MatchRepository matchRepository, PropPositionRepository propPositionRepository, ResultPositionRepository resultPositionRepository) {
        this.matchRepository = matchRepository;
        this.propPositionRepository = propPositionRepository;
        this.resultPositionRepository = resultPositionRepository;
    }

    public void processUpdate(MatchUpdate update) {
        //for now we are just saving the update to mongodb.
        //in the future we will check to see if it is a genuine update and we will send to stream
        matchRepository.save(update.getMatch());
        for (Position position : update.getPositions()) {
            if(position instanceof PropPosition propPosition) {
                propPositionRepository.save(propPosition);
            } else if(position instanceof ResultPosition resultPosition) {
                resultPositionRepository.save(resultPosition);
            }
        }
    }
}
