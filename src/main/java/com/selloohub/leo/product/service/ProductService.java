package com.selloohub.leo.product.service;

import com.selloohub.leo.common.exception.ConflictException;
import com.selloohub.leo.common.exception.ResourceNotFoundException;
import com.selloohub.leo.common.response.PageResponse;
import com.selloohub.leo.product.dto.*;
import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.model.ProductStatus;
import com.selloohub.leo.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsBySku(request.sku())) {
            throw new ConflictException("SKU_EXISTS", "SKU already exists");
        }

        Product product = new Product(request.sku(), request.name(), request.retailPrice());
        product.setDescription(request.description());
        product.setWeightGrams(request.weightGrams());
        product.setImageUrls(request.imageUrls());
        productRepository.save(product);

        return ProductResponse.from(product);
    }

    public PageResponse<ProductResponse> getListProducts(ProductStatus status, String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> productPage = productRepository.searchActive(status, search, pageable)
                .map(ProductResponse::from);

        return PageResponse.from(productPage);
    }

    public ProductResponse getProduct(String id) {
        Product product = productRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ProductResponse.from(product);
    }

    public ProductResponse updateProduct(String id, UpdateProductRequest request) {
        Product product = productRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setRetailPrice(request.retailPrice());
        product.setWeightGrams(request.weightGrams());
        product.setImageUrls(request.imageUrls());

        productRepository.save(product);
        return ProductResponse.from(product);
    }

    public ProductResponse updateProductStatus(String id, UpdateProductStatusRequest request) {
        Product product = productRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setStatus(request.status());
        productRepository.save(product);
        return ProductResponse.from(product);
    }

    public void deleteProduct(String id) {
        Product product = productRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.markDeleted();
        productRepository.save(product);
    }

    public PageResponse<ProductResponse> getListPublicProducts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> productPage = productRepository.searchActive(ProductStatus.ACTIVE, null, pageable)
                .map(ProductResponse::from);

        return PageResponse.from(productPage);
    }

    public ProductResponse getPublicProductBySku(String sku) {
        Product product = productRepository.findBySkuAndDeletedFalseAndStatus(sku, ProductStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return ProductResponse.from(product);
    }


}
