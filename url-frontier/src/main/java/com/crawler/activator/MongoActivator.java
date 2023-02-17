package com.crawler.activator;

import com.crawler.entity.Request;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * author  danial
 * email  doneskandari@gmail.com
 */
@Component
public class MongoActivator {

    private final MongoTemplate mongoTemplate;

    public MongoActivator(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Request find(@Payload Request request) {
        Query query = new Query(Criteria.where("link").is(request.getLink()));
        return mongoTemplate.findOne(query, Request.class, "urls");
    }

    public void push(@Payload Request request) {
        if (null != find(request))
            mongoTemplate.save(request, "urls");
    }
}
