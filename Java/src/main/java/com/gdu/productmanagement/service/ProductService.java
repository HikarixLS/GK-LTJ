package com.gdu.productmanagement.service;

import com.gdu.productmanagement.entity.Product;
import com.gdu.productmanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        log.info("Creating new product with code: {}", product.getProductCode());

        if (productRepository.existsByProductCode(product.getProductCode())) {
            throw new RuntimeException("Mã sản phẩm đã tồn tại: " + product.getProductCode());
        }

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        log.info("Updating product with id: {}", id);

        Product product = findById(id);

        // Check if productCode is being changed and if new code already exists
        if (!product.getProductCode().equals(productDetails.getProductCode()) &&
                productRepository.existsByProductCode(productDetails.getProductCode())) {
            throw new RuntimeException("Mã sản phẩm đã tồn tại: " + productDetails.getProductCode());
        }

        // Update fields
        product.setProductCode(productDetails.getProductCode());
        product.setProductName(productDetails.getProductName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setImageUrl(productDetails.getImageUrl());
        product.setCategory(productDetails.getCategory());
        product.setStatus(productDetails.getStatus());
        product.setWeight(productDetails.getWeight());
        product.setDimensions(productDetails.getDimensions());
        product.setBrand(productDetails.getBrand());
        product.setManufacturer(productDetails.getManufacturer());
        product.setWarrantyPeriod(productDetails.getWarrantyPeriod());
        product.setStore(productDetails.getStore());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        Product product = findById(id);
        productRepository.delete(product);
    }

    public void updateStock(Long id, Integer quantity) {
        log.info("Updating stock for product id: {} with quantity: {}", id, quantity);
        Product product = findById(id);

        int newStock = product.getStockQuantity() + quantity;
        if (newStock < 0) {
            throw new RuntimeException("Số lượng tồn kho không thể âm");
        }

        product.setStockQuantity(newStock);

        // Update status based on stock
        if (newStock == 0) {
            product.setStatus(Product.ProductStatus.OUT_OF_STOCK);
        } else if (product.getStatus() == Product.ProductStatus.OUT_OF_STOCK) {
            product.setStatus(Product.ProductStatus.ACTIVE);
        }

        productRepository.save(product);
    }

    public void deactivateProduct(Long id) {
        log.info("Deactivating product with id: {}", id);
        Product product = findById(id);
        product.setStatus(Product.ProductStatus.INACTIVE);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
    }

    @Transactional(readOnly = true)
    public Optional<Product> findByProductCode(String productCode) {
        return productRepository.findByProductCode(productCode);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Product> searchProducts(String searchTerm, Product.ProductCategory category,
            Product.ProductStatus status, Pageable pageable) {
        return productRepository.findBySearchCriteria(searchTerm, category, status, pageable);
    }

    @Transactional(readOnly = true)
    public List<Product> findByProductName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    @Transactional(readOnly = true)
    public List<Product> findByCategory(Product.ProductCategory category) {
        return productRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Product> findByStatus(Product.ProductStatus status) {
        return productRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Product> findByStore(Long storeId) {
        return productRepository.findByStoreId(storeId);
    }

    @Transactional(readOnly = true)
    public List<Product> findByBrand(String brand) {
        return productRepository.findByBrandContainingIgnoreCase(brand);
    }

    @Transactional(readOnly = true)
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Transactional(readOnly = true)
    public List<Product> findLowStockProducts(Integer threshold) {
        return productRepository.findLowStockProducts(threshold);
    }

    @Transactional(readOnly = true)
    public Long countActiveProducts() {
        return productRepository.countActiveProducts();
    }

    @Transactional(readOnly = true)
    public long countAllProducts() {
        return productRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getProductStatsByCategory() {
        return productRepository.countProductsByCategory();
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalInventoryValue() {
        return productRepository.calculateTotalInventoryValue();
    }

    @Transactional(readOnly = true)
    public boolean existsByProductCode(String productCode) {
        return productRepository.existsByProductCode(productCode);
    }
}
