package com.selloohub.leo.product.controller;

import com.selloohub.leo.common.response.ApiResponse;
import com.selloohub.leo.product.dto.CreateBundleProductRequest;
import com.selloohub.leo.product.dto.CreateComboProductRequest;
import com.selloohub.leo.product.dto.CreateSimpleProductRequest;
import com.selloohub.leo.product.dto.ProductResponse;
import com.selloohub.leo.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody CreateSimpleProductRequest request) {

        ProductResponse response = productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PostMapping("/bundle")
    public ResponseEntity<ApiResponse<ProductResponse>> createProductBundle(
            @Valid @RequestBody CreateBundleProductRequest request) {

        ProductResponse response = productService.createBundle(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PostMapping("/combo")
    public ResponseEntity<ApiResponse<ProductResponse>> createCombo(
            @Valid @RequestBody CreateComboProductRequest request) {
        ProductResponse response = productService.createCombo(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }
}
