package app.controller;

import app.model.Vendor;
import app.service.VendorService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/vendors")
public class VendorController {

  private final VendorService vendorService;

  // @Autowired
  public VendorController(VendorService vendorService) {
    this.vendorService = vendorService;
  }

  // @GetMapping
  public Page<Vendor> getAllVendors(Pageable pageable) {
    return vendorService.getAllVendors(pageable);
  }
}
