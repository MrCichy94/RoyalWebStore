package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("should create product object with no-args")
    void should_create_product_obj(){
        Product eg1 = new Product();

        assertNotEquals(null, eg1);
    }

    @Test
    @DisplayName("should create product object with args")
    void should_create_product_obj_with_args(){
        Product eg2 = new Product(1,"First", new BigDecimal("9.99"), new BigDecimal("0.23"));

        assertEquals("First", eg2.productName);
    }

    @Test
    @DisplayName("should initialize properly product vat")
    void should_properly_count_vatValue(){
        Product eg3 = new Product(1,"First", new BigDecimal("200.00"), new BigDecimal("0.10"));

        assertEquals(new BigDecimal("20.00"), eg3.vatValue);
    }

    @Test
    @DisplayName("should initialize properly product base net price")
    void should_properly_count_baseNetPrice(){
        Product eg4 = new Product(1,"First", new BigDecimal("200.00"), new BigDecimal("0.10"));

        assertEquals(new BigDecimal("180.00"), eg4.baseNetPrice);
    }
}
