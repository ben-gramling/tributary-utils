package com.arbriver.tributaryutils.lib.service;

import com.arbriver.tributaryutils.lib.dal.MatchRepository;
import com.arbriver.tributaryutils.lib.dal.PositionRepository;
import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import com.arbriver.tributaryutils.lib.model.service.ReactiveProcessor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BasicReactiveProcessor implements ReactiveProcessor {

    private final MatchRepository matchRepository;
    private final PositionRepository positionRepository;

    public BasicReactiveProcessor(MatchRepository matchRepository, PositionRepository positionRepository) {
        this.matchRepository = matchRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public Flux<Object> processUpdates(Publisher<MatchUpdate> _update) {
        return Flux.from(_update).flatMap(update ->
                Flux.merge(matchRepository.save(update.match()),
                        positionRepository.saveAll(update.positions()))
        );
    }
}
