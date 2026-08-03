package com.selloohub.leo.product.repository;

import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.model.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public ProductRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Product> searchActive(ProductStatus status, String search, Pageable pageable) {

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("deleted").is(false));

        if (status != null) {
            criteria.add(Criteria.where("status").is(status));
        }

        if (search != null && !search.isBlank()) {
            criteria.add(new Criteria().orOperator(
                    Criteria.where("name").regex(search, "i"),
                    Criteria.where("sku").regex(search, "i")
            ));
        }

        Query query = new Query(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        List<Product> content = mongoTemplate.find(query.with(pageable), Product.class);

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> mongoTemplate.count(query, Product.class)
        );
    }
}
