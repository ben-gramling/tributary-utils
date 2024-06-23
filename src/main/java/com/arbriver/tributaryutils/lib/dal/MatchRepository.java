package com.arbriver.tributaryutils.lib.dal;

import com.arbriver.tributaryutils.lib.model.Match;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MatchRepository extends ReactiveMongoRepository<Match, String> {
    @Aggregation({"""
        {
           '$search': {
            'index': 'matchSearchIndex',
            'compound': {
                'should': [
                    {
                        'text':  {
                            'query': '$?1',
                            'path': 'awayName',
                            'fuzzy': { maxEdits: 2 }
                        }
                    },
                    {
                        'text':  {
                            'query': '$?0',
                            'path': 'homeName',
                            'fuzzy': { maxEdits: 2 }
                        }
                    }
                ],
            minimumShouldMatch: 2
            }
        }}
   """})
    Mono<Match> findSimilarMatch(String homeName, String awayName);
}