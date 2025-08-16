package app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, UUID> {
  Optional<Vendor> findByVendorName(String vendorName);

  Optional<Vendor> findByVendorIdentifier(UUID identifier);

  Boolean existsByVendorName(String vendorName);
}
