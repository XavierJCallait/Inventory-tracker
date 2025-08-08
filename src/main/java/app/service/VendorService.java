package app.service;

import app.model.Vendor;
import app.repository.VendorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
  private final VendorRepository vendorRepository;

  @Autowired
  public VendorService(VendorRepository vendorRepository) {
    this.vendorRepository = vendorRepository;
  }

  public Vendor createVendor(Vendor vendor) {
    return vendorRepository.save(vendor);
  }

  public List<Vendor> getAllVendors() {
    return vendorRepository.findAll();
  }
}
