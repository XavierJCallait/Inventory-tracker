package app.repository;

import app.model.Vendor;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, UUID> {
  Optional<Vendor> findByVendorName(String vendorName);

  Optional<Vendor> findByVendorIdentifier(UUID identifier);

  Boolean existsByVendorName(String vendorName);

  Page<Vendor> findAllVendors(Pageable pageable);
}
