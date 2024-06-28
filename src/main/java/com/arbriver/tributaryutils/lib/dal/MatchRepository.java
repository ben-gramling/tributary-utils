package com.arbriver.tributaryutils.lib.dal;

import com.arbriver.tributaryutils.lib.model.Match;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;

@Repository
public interface MatchRepository extends ReactiveMongoRepository<Match, String> {
    @Aggregation(pipeline =  {"""
        {
           '$search': {
            'index': 'matchSearchIndex',
            'compound': {
                'should': [
                    {
                        'text':  {
                            'query': '?1',
                            'path': 'awayName',
                            'fuzzy': { maxEdits: 2 }
                        }
                    },
                    {
                        'text':  {
                            'query': '?0',
                            'path': 'homeName',
                            'fuzzy': { maxEdits: 2 }
                        }
                    },
                    {
                        'range': {
                            'path': 'startTime',
                            'gte': ?2,
                            'lte': ?3,
                        }
                    }
                ],
            minimumShouldMatch: 3
            }
        }}
   """})
    Mono<Match> findSimilarMatchFuzzy(String homeName, String awayName, Instant lowerBound, Instant upperBound);

    @Aggregation(pipeline =  {"""
        {
           '$search': {
            'index': 'matchSearchIndex',
            'compound': {
                'should': [
                    {
                        'text':  {
                            'query': '?1',
                            'path': 'awayName',
                        }
                    },
                    {
                        'text':  {
                            'query': '?0',
                            'path': 'homeName',
                        }
                    },
                    {
                        'range': {
                            'path': 'startTime',
                            'gte': ?2,
                            'lte': ?3,
                        }
                    }
                ],
            minimumShouldMatch: 2
            }
        }}
   """})
    Mono<Match> findSimilarMatchOneSide(String homeName, String awayName, Instant lowerBound, Instant upperBound);

    @Query(value = "{ numBooks: { $gt: 1}}")
    Flux<Match> findMatchesWithMultipleBooks();
}