package com.arbriver.tributaryutils.lib.dal;

import com.arbriver.tributaryutils.lib.model.Position;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface PositionRepository extends ReactiveMongoRepository<Position, String> {
    @Query(value = "{ matchRefId: '?0' }")
    Flux<Position> findAllByMatchRefID(String matchRefID);
}
