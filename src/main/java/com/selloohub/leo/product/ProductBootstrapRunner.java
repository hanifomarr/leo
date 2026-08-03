package com.selloohub.leo.product;

import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.product.bootstrap.enabled", havingValue = "true")
public class ProductBootstrapRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ProductBootstrapRunner.class);
    private final ProductRepository productRepository;

    public ProductBootstrapRunner(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Product> fixtures = List.of(
                fixture("SKU-0001", "Classic Tumbler 500ml", "25.90", 300),
                fixture("SKU-0002", "Notebook A5 Ruled", "8.50", 150),
                fixture("SKU-0003", "Wireless Mouse", "39.90", 120),
                fixture("SKU-0004", "Canvas Tote Bag", "19.90", 200),
                fixture("SKU-0005", "Ceramic Mug 350ml", "15.90", 350),
                fixture("SKU-0006", "Desk Organizer", "29.90", 400)
        );

        for (Product product : fixtures) {
            if (productRepository.existsBySku(product.getSku())) {
                log.info("Product bootstrap skipped — sku '{}' already exists", product.getSku());
                continue;
            }
            productRepository.save(product);
            log.info("Product seeded: {}", product.getSku());
        }
    }

    private static Product fixture(String sku, String name, String retailPrice, int weightGrams) {
        Product product = new Product(sku, name, new BigDecimal(retailPrice));
        product.setWeightGrams(weightGrams);
        return product;
    }
}

