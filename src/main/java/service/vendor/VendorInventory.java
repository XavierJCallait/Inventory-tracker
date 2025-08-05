package service.vendor;

import service.Inventory;

public class VendorInventory extends Inventory {
  private final String vendorName;

  public VendorInventory(String vendorName) {
    this.vendorName = vendorName;
  }

  public String getVendorName() {
    return vendorName;
  }
}
