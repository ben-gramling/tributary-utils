package com.arbriver.tributaryutils.lib.dal;

import com.arbriver.tributaryutils.lib.model.Position;
import com.arbriver.tributaryutils.lib.model.constants.PropBetType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends MongoRepository<Position, String> {

}
