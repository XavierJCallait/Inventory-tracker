package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "vendors")
public class Vendor {
  @Id
  @GeneratedValue
  @JdbcTypeCode(SqlTypes.VARCHAR)
  @Column(length = 36, updatable = false)
  private UUID vendorIdentifier;

  @Column(nullable = false, length = 100, unique = true)
  private String vendorName;

  @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
  private final Set<Product> products = new HashSet<>();

  public Vendor() {}

  public Vendor(String vendorName) {
    this.vendorName = vendorName;
  }

  public String getVendorName() {
    return this.vendorName;
  }

  public UUID getIdentifier() {
    return this.vendorIdentifier;
  }

  public Set<Product> getProducts() {
    return Collections.unmodifiableSet(this.products);
  }
}
