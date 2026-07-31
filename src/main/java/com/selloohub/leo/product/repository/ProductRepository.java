package com.selloohub.leo.product.repository;

import com.selloohub.leo.common.repository.SoftDeleteRepository;
import com.selloohub.leo.product.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends SoftDeleteRepository<Product, String> {
    boolean existsBySku(String sku);
}
