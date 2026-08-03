package com.selloohub.leo.product.controller;

import com.selloohub.leo.common.response.ApiResponse;
import com.selloohub.leo.common.response.PageResponse;
import com.selloohub.leo.product.dto.ProductResponse;
import com.selloohub.leo.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/products")
public class PublicProductController {

    private final ProductService productService;

    public PublicProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getAllPublicProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<ProductResponse> response = productService.getListPublicProducts(page, size);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ApiResponse<ProductResponse>> getPublicProductBySku(
            @PathVariable String sku) {
        ProductResponse response = productService.getPublicProductBySku(sku);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }
}

