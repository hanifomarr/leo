package com.selloohub.leo.product.controller;

import com.selloohub.leo.common.response.ApiResponse;
import com.selloohub.leo.common.response.PageResponse;
import com.selloohub.leo.product.dto.CreateProductRequest;
import com.selloohub.leo.product.dto.ProductResponse;
import com.selloohub.leo.product.dto.UpdateProductRequest;
import com.selloohub.leo.product.dto.UpdateProductStatusRequest;
import com.selloohub.leo.product.model.ProductStatus;
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
            @Valid @RequestBody CreateProductRequest request) {

        ProductResponse response = productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getAllProducts(
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        PageResponse<ProductResponse> response = productService.getListProducts(status, search, page, size);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(
            @PathVariable("id") String id) {
        ProductResponse response = productService.getProduct(id);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProductStatus(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateProductStatusRequest request) {
        ProductResponse response = productService.updateProductStatus(id, request);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable("id") String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
