package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("should create product object with no-args")
    void shouldCreateProductObj() {
        Product eg1 = new Product();

        assertNotEquals(null, eg1);
    }

    @Test
    @DisplayName("should create product object with args")
    void shouldCreateProductObjWithArgs() {
        Product eg2 = new Product(1, "First", "Nike",
                "Obuwie", new BigDecimal("9.99"), new BigDecimal("0.23"));

        assertEquals("First", eg2.getProductName());
    }

    @Test
    @DisplayName("should initialize properly product vat")
    void shouldProperlyCountVatValue() {
        Product eg3 = new Product(1, "First", "Nike",
                "Obuwie", new BigDecimal("200.00"), new BigDecimal("0.10"));

        assertEquals(new BigDecimal("20.00"), eg3.getVatValue());
    }

    @Test
    @DisplayName("should initialize properly product base net price")
    void shouldProperlyCountBaseNetPrice() {
        Product eg4 = new Product(1, "First", "Nike",
                "Obuwie", new BigDecimal("200.00"), new BigDecimal("0.10"));

        assertEquals(new BigDecimal("180.00"), eg4.getSellBaseNetPrice());
    }
}
