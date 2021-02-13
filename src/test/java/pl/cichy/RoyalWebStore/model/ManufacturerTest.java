package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerTest {

    @Test
    @DisplayName("should create manufacturer object with no-args")
    void shouldCreateManufacturer_obj(){
        Manufacturer eg1 = new Manufacturer();

        assertNotEquals(null, eg1);
    }

    @Test
    @DisplayName("should create manufacturer object with args")
    void shouldCreateManufacturerObjWithArgs(){
        Manufacturer eg2 = new Manufacturer("First");

        assertEquals("First", eg2.manufacturerName);
    }
}
