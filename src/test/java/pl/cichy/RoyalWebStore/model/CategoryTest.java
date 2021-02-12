package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    @DisplayName("should create category object with no-args")
    void should_create_category_obj(){
        Category eg1 = new Category();

        assertNotEquals(null, eg1);
    }

    @Test
    @DisplayName("should create category object with args")
    void should_create_category_obj_with_args(){
        Category eg2 = new Category("First");

        assertEquals("First", eg2.categoryName);
    }
}
