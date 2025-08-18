package app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
  Optional<Product> findByProductIdentifier(UUID identifier);

  // Page<Product> findProductsByProductName(String productName, Pageable pageable);

  // Page<Product> findAllProducts(Pageable pageable);

  // Page<Product> findProductsByVendorName(String vendorName, Pageable pageable);

  // Page<Product> findProductsByVendorIdentifier(UUID vendorIdentifier, Pageable pageable);

  // Optional<Product> findByVendorAndProductName(String vendorName, String productName);

  // Optional<Product> findByVendorIdentifierAndProductName(UUID vendorIdentifier, String productName);

  // Boolean existsByProductName(String productName);
}
