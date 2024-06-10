package com.arbriver.tributaryutils.lib.dal;

import com.arbriver.tributaryutils.lib.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultPositionRepository extends MongoRepository<Match, String> {
}
