package com.selloohub.leo.stock.service;

import com.selloohub.leo.common.exception.ConflictException;
import com.selloohub.leo.common.exception.ResourceNotFoundException;
import com.selloohub.leo.common.response.PageResponse;
import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.repository.ProductRepository;
import com.selloohub.leo.stock.dto.AdjustStockRequest;
import com.selloohub.leo.stock.dto.StockLevelResponse;
import com.selloohub.leo.stock.dto.StockMovementResponse;
import com.selloohub.leo.stock.model.StockMovement;
import com.selloohub.leo.stock.model.StockMovementReason;
import com.selloohub.leo.stock.repository.StockMovementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public StockService(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public PageResponse<StockLevelResponse> getStockLevels(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StockLevelResponse> stockLevel = productRepository.searchActive(null, null, pageable)
                .map(StockLevelResponse::from);

        return PageResponse.from(stockLevel);
    }

    public StockMovementResponse adjustStock(String productId, AdjustStockRequest request, String actor) {
        if (request.reason() == StockMovementReason.INITIAL) {
            if (stockMovementRepository.existsByProductId(productId)) {
                throw new ConflictException("DUPLICATE_INITIAL_STOCK",
                        "Product already has stock movements — use MANUAL_ADJUST");
            }
        } else if (request.reason() != StockMovementReason.MANUAL_ADJUSTMENT) {
            throw new ConflictException("INVALID_REASON", "Please select valid reason");
        }

        productRepository.findActiveById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));


        Product updated = productRepository.applyStockDelta(productId, request.delta())
                .orElseThrow(() -> new ConflictException("INSUFFICIENT_STOCK", "Adjustment would take stock below zero"));

        StockMovement movement = new StockMovement(productId, request.reason(), request.delta(), updated.getStockQty(), null, request.note(), actor);
        stockMovementRepository.save(movement);

        return StockMovementResponse.from(movement);
    }

    public Optional<StockMovementResponse> decrementForSale(String productId, int qty, String orderId) {
        Optional<Product> updated = productRepository.applyStockDelta(productId, -qty);
        if (updated.isEmpty()) {
            return Optional.empty();
        }

        StockMovement movement = new StockMovement(productId, StockMovementReason.ORDER, -qty, updated.get().getStockQty(), orderId, null, "SYSTEM");
        stockMovementRepository.save(movement);

        return Optional.of(StockMovementResponse.from(movement));
    }

    public PageResponse<StockMovementResponse> getMovements(String productId, int page, int size) {
        productRepository.findActiveById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<StockMovementResponse> movementPage = stockMovementRepository.findByProductIdOrderByCreatedAtDesc(productId, pageable)
                .map(StockMovementResponse::from);

        return PageResponse.from(movementPage);

    }


}
