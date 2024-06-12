package com.arbriver.tributaryutils.lib.dal;

import com.arbriver.tributaryutils.lib.model.PropPosition;
import com.arbriver.tributaryutils.lib.model.ResultPosition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropPositionRepository extends MongoRepository<PropPosition, String> {

}
