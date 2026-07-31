package com.selloohub.leo.product.validation;

import com.selloohub.leo.product.dto.ComboOptionGroupRequest;
import com.selloohub.leo.product.dto.ComboOptionRequest;
import com.selloohub.leo.product.exception.ComboInvalidException;
import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.model.ProductStatus;
import com.selloohub.leo.product.model.ProductType;
import com.selloohub.leo.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComboValidator {

    private final ProductRepository productRepository;

    public ComboValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validate(List<ComboOptionGroupRequest> groups) {
        for (ComboOptionGroupRequest group : groups) {

            if (group.chooseCount() > group.options().size()) {
                throw new ComboInvalidException("chooseCount (" + group.chooseCount() + ") exceeds options available in group: "
                        + group.groupName());
            }

            for (ComboOptionRequest option : group.options()) {
                Product product = productRepository.findActiveById(option.productId())
                        .orElseThrow(() -> new ComboInvalidException(
                                "Option product not found or inactive: " + option.productId()));

                if (product.getType() != ProductType.SIMPLE) {
                    throw new ComboInvalidException(
                            "Option must be a SIMPLE product: " + option.productId());
                }

                if (product.getStatus() != ProductStatus.ACTIVE) {
                    throw new ComboInvalidException(
                            "Option must be ACTIVE: " + option.productId()
                    );
                }
            }
        }
    }
}
