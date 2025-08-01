package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Product model.
 */
class ProductTest {
    @Test
    void testProductInitialization() {
        Product product = new Product("laptop", 1000.00, "Electronics", "Warehouse A", 50);
        assertNotNull(product);
        assertEquals("laptop", product.getName());
        assertEquals(1000.00, product.getPrice());
        assertEquals("Electronics", product.getCategory());
        assertEquals("Warehouse A", product.getLocation());
        assertEquals(50, product.getQuantity());
    }

    @Test
    void testProductUpdate() {
        Product product = new Product("laptop", 1000.00, "Electronics", "Warehouse A", 50);
        product.changePrice(950.00);
        assertEquals(950.00, product.getPrice());
    }
}