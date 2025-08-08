package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendors")
public class Vendor {
  @JdbcTypeCode(SqlTypes.VARCHAR)
  @Column(length = 36)
  @Id
  private UUID identifier;

  @Column(nullable = false, length = 100)
  private String vendorName;

  @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
  private Set<Product> products = new HashSet<>();

  public Vendor() {}

  public Vendor(String vendorName, UUID identifier) {
    this.vendorName = vendorName;
    this.identifier = identifier;
  }

  public String getVendorName() {
    return this.vendorName;
  }

  public UUID getIdentifier() {
    return this.identifier;
  }

  public Set<Product> getProducts() {
    return Collections.unmodifiableSet(this.products);
  }
}
