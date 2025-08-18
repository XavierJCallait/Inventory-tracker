package app.repository;

import app.model.Product;
import app.model.Vendor;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
  Optional<Product> findByProductIdentifier(UUID identifier);

  Page<Product> findByProductName(String productName, Pageable pageable);

  Page<Product> findByVendor(Vendor vendor, Pageable pageable);

  Optional<Product> findByVendorAndProductName(Vendor vendor, String productName);

  Page<Product> findAllByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

  Page<Product> findAllByDiscountGreaterThan(Double discount, Pageable pageable);

  Boolean existsByProductName(String productName);
}
