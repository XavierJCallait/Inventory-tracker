package app.repository;

import app.model.Vendor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, UUID> {
  Optional<Vendor> findByVendorName(String vendorName);

  List<Vendor> findByVendorIdentifier(UUID identifier);
}
