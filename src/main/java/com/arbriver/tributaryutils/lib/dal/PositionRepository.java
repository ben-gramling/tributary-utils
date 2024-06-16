package com.arbriver.tributaryutils.lib.dal;

import com.arbriver.tributaryutils.lib.model.Position;
import com.arbriver.tributaryutils.lib.model.constants.PropBetType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends MongoRepository<Position, String> {
    @Query(value = "{ matchRefId: '?0' }")
    List<Position> findAllByMatchRefID(String matchRefID);
}
