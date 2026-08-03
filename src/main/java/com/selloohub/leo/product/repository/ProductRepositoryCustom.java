package com.selloohub.leo.product.repository;

import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.model.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> searchActive(ProductStatus status, String search, Pageable pageable);
}
