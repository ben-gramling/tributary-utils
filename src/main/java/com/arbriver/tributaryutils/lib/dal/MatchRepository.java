package com.arbriver.tributaryutils.lib.dal;

import com.arbriver.tributaryutils.lib.model.Match;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends ReactiveMongoRepository<Match, String> {

}