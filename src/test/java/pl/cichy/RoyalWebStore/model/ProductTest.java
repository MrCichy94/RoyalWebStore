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
        Product eg3 = new Product(1,"First", new BigDecimal("9.99"));

        assertEquals("First", eg3.productName);
    }
}
