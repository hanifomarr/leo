package com.selloohub.leo.product.service;

import com.selloohub.leo.common.exception.ConflictException;
import com.selloohub.leo.common.exception.ResourceNotFoundException;
import com.selloohub.leo.product.dto.*;
import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.model.ProductType;
import com.selloohub.leo.product.repository.ProductRepository;
import com.selloohub.leo.product.validation.BundleValidator;
import com.selloohub.leo.product.validation.ComboValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BundleValidator bundleValidator;
    private final ComboValidator comboValidator;

    public ProductService(ProductRepository productRepository, BundleValidator bundleValidator, ComboValidator comboValidator) {
        this.productRepository = productRepository;
        this.bundleValidator = bundleValidator;
        this.comboValidator = comboValidator;
    }

    public ProductResponse createProduct(CreateSimpleProductRequest request) {

        requireSkuAvailable(request.sku());
        Product product = new Product(request.sku(), request.name(), ProductType.SIMPLE, request.retailPrice());
        product.setDescription(request.description());
        product.setWeightGrams(request.weightGrams());
        product.setImageUrls(request.imageUrls());
        productRepository.save(product);

        return ProductResponse.from(product);
    }

    public ProductResponse createBundle(CreateBundleProductRequest request) {

        requireSkuAvailable(request.sku());
        bundleValidator.validate(request.components());
        Product product = new Product(request.sku(), request.name(), ProductType.BUNDLE, request.retailPrice());
        product.setComponents(mapComponents(request.components()));
        productRepository.save(product);

        return ProductResponse.from(product);
    }

    public ProductResponse createCombo(CreateComboProductRequest request) {

        requireSkuAvailable(request.sku());
        comboValidator.validate(request.comboGroups());
        Product product = new Product(request.sku(), request.name(), ProductType.COMBO, request.retailPrice());
        product.setComboGroups(mapCombos(request.comboGroups()));
            productRepository.save(product);

        return ProductResponse.from(product);
    }

    private void requireSkuAvailable(String sku) {
        if (productRepository.existsBySku(sku)) {
            throw new ConflictException("SKU_EXISTS", "SKU already exists");
        }
    }


    private List<Product.BundleComponent> mapComponents(List<BundleComponentRequest> requests) {
        return requests.stream()
                .map(c -> new Product.BundleComponent(c.productId(), c.quantity()))
                .toList();
    }

    private List<Product.ComboOptionGroup> mapCombos(List<ComboOptionGroupRequest> requests) {
        return requests.stream()
                .map(g -> new Product.ComboOptionGroup(
                        g.groupName(),
                        g.chooseCount(),
                        g.options().stream()
                                .map(o -> new Product.ComboOption(o.productId(), o.additionalPrice()))
                                .toList()))
                .toList();
    }

}
