package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    @DisplayName("should create category object with no-args")
    void shouldCreateCategoryObj() {
        Category eg1 = new Category();

        assertNotEquals(null, eg1);
    }

    @Test
    @DisplayName("should create category object with args")
    void shouldCreateCategoryObjWithArgs() {
        Category eg2 = new Category("First");

        assertEquals("First", eg2.getCategoryName());
    }
}
