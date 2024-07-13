package com.infinity.ecommerce.product;

import com.infinity.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(ProductRequest request) {
        var product = productMapper.toProduct(request);
        var savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {
        var productIds = request.stream().map(ProductPurchaseRequest::productId).toList();
        var storedProducts =  productRepository.findAllByIdInOrderById(productIds);
        if(productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exists.");
        }

        var sortedRequest = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i = 0; i < sortedRequest.size(); i++) {
            var purchaseRequest = sortedRequest.get(i);
            var storedProduct = storedProducts.get(i);
            if(purchaseRequest.quantity() > storedProduct.getAvailableQuantity()) {
                throw new ProductPurchaseException("Not enough stock for product: " + storedProduct.getId());
            }
            storedProduct.setAvailableQuantity(storedProduct.getAvailableQuantity() - purchaseRequest.quantity());
            productRepository.save(storedProduct);
            purchasedProducts
                    .add(productMapper.toProductPurchaseResponse(storedProduct, purchaseRequest.quantity()));
        }
        return purchasedProducts;
    }

    public List<ProductResponse> findAllProducts() {

        return productRepository
                .findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }
}
