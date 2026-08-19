package com.selloohub.leo.product.repository;

import com.selloohub.leo.common.repository.SoftDeleteRepository;
import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.model.ProductStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends SoftDeleteRepository<Product, String, ProductStatus>, ProductRepositoryCustom {

    boolean existsBySku(String sku);

    Optional<Product> findBySkuAndDeletedFalseAndStatus(String sku, ProductStatus status);
}
