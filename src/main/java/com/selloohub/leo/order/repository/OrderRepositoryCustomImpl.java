package com.selloohub.leo.order.repository;

import com.selloohub.leo.order.model.Order;
import com.selloohub.leo.order.model.OrderStatus;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Optional;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public OrderRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Order> transitionStatus(String orderId, OrderStatus expected, OrderStatus next) {

        Query query = Query.query(
                Criteria.where("_id").is(orderId)
                        .and("status").is(expected)
        );

        Update update = new Update().set("status", next);
        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);
        Order result = mongoTemplate.findAndModify(query, update, options, Order.class);

        return Optional.ofNullable(result);
    }
}
