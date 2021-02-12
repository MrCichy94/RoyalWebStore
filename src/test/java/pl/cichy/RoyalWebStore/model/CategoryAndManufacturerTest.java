package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryAndManufacturerTest {

    @Test
    @DisplayName("should create categoryAndManufacturer object with no-args")
    void should_create_categoryAndManufacturer_obj(){
        CategoryAndManufacturer eg1 = new CategoryAndManufacturer();

        assertNotEquals(null, eg1);
    }

}
