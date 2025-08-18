package app.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.Vendor;
import app.repository.VendorRepository;

@Service
@Transactional
public class VendorService {
  private final VendorRepository vendorRepository;

  @Autowired
  public VendorService(VendorRepository vendorRepository) {
    this.vendorRepository = vendorRepository;
  }

  public Vendor createVendor(Vendor vendor) {
    Vendor existingVendor = vendorRepository.findByVendorName(vendor.getVendorName()).orElse(null);
    if (existingVendor != null) {
      return existingVendor;
    }
    return vendorRepository.save(vendor);
  }

  @Transactional(readOnly = true)
  public Vendor getVendorById(UUID vendorId) {
    return vendorRepository.findByVendorIdentifier(vendorId).orElse(null);
  }

  @Transactional(readOnly = true)
  public Vendor getVendorByName(String vendorName) {
    return vendorRepository.findByVendorName(vendorName).orElse(null);
  }

  @Transactional(readOnly = true)
  public Page<Vendor> getAllVendors(Pageable pageable) {
    return vendorRepository.findAll(pageable);
  }
}
