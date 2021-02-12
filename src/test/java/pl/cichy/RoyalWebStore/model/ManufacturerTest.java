package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerTest {

    @Test
    @DisplayName("should create manufacturer object with no-args")
    void should_create_manufacturer_obj(){
        Manufacturer eg1 = new Manufacturer();

        assertNotEquals(null, eg1);
    }

    @Test
    @DisplayName("should create manufacturer object with args")
    void should_create_manufacturer_obj_with_args(){
        Manufacturer eg2 = new Manufacturer("First");

        assertEquals("First", eg2.manufacturerName);
    }

}
