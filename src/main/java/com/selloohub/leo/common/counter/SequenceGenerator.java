package com.selloohub.leo.common.counter;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class SequenceGenerator {

    private final MongoTemplate mongoTemplate;


    public SequenceGenerator(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public long nextValue(String sequenceName) {
        Query query = Query.query(where("_id").is(sequenceName));
        Update update = new Update().inc("seq", 1);

        FindAndModifyOptions options = FindAndModifyOptions.options()
                .returnNew(true)
                .upsert(true);

        Counter counter = mongoTemplate.findAndModify(query, update, options, Counter.class);

        return counter.getSeq();
    }
}
