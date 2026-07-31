package com.selloohub.leo.product.validation;

import com.selloohub.leo.product.dto.BundleComponentRequest;
import com.selloohub.leo.product.exception.BundleInvalidException;
import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.model.ProductStatus;
import com.selloohub.leo.product.model.ProductType;
import com.selloohub.leo.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BundleValidator {

    private final ProductRepository productRepository;

    public BundleValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validate(List<BundleComponentRequest> components) {

        for (BundleComponentRequest component : components) {
            Product product = productRepository.findActiveById(component.productId())
                    .orElseThrow(() -> new BundleInvalidException("Component product not found or inactive: " + component.productId()));

            if (product.getType() != ProductType.SIMPLE) {
                throw new BundleInvalidException("Product type is not SIMPLE: " + component.productId());
            }

            if (product.getStatus() != ProductStatus.ACTIVE) {
                throw new BundleInvalidException("Product status is not ACTIVE: " + component.productId());
            }
        }
    }

}
