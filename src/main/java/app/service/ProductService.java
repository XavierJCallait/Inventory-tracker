package app.service;

import app.model.Product;
import app.model.Vendor;
import app.repository.ProductRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class ProductService {
  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public <T extends Product> T createProduct(T product) {
    return productRepository.save(product);
  }

  @Transactional(readOnly = true)
  public Product findProductById(UUID productId) {
    return productRepository.findByProductIdentifier(productId).orElse(null);
  }

  @Transactional(readOnly = true)
  public Page<Product> findProductsByName(String productName, Pageable pageable) {
    return productRepository.findByProductName(productName, pageable);
  }

  @Transactional(readOnly = true)
  public Page<Product> findProductsByVendor(Vendor vendor, Pageable pageable) {
    return productRepository.findByVendor(vendor, pageable);
  }

  @Transactional(readOnly = true)
  public Product findProductByVendorAndName(Vendor vendor, String productName) {
    return productRepository.findByVendorAndProductName(vendor, productName).orElse(null);
  }

  @Transactional(readOnly = true)
  public Page<Product> findProductsByMinimumDiscount(Double discount, Pageable pageable) {
    return productRepository.findAllByDiscountGreaterThan(discount, pageable);
  }

  @Transactional(readOnly = true)
  public Page<Product> findProductsByPriceRange(
      Double minPrice, Double maxPrice, Pageable pageable) {
    return productRepository.findAllByPriceBetween(minPrice, maxPrice, pageable);
  }

  @Transactional(readOnly = true)
  public Page<Product> findProductsByCategory(String category, Pageable pageable) {
    return productRepository.findByType(category, pageable);
  }

  @Transactional(readOnly = true)
  public Page<Product> getAllProducts(Pageable pageable) {
    return productRepository.findAll(pageable);
  }
}
