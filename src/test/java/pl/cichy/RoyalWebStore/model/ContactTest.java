package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    @Test
    @DisplayName("should create contact object with no-args")
    void shouldCreateContactObj() {
        Contact eg1 = new Contact();

        assertNotEquals(null, eg1);
    }

    @Test
    @DisplayName("should create contact object with args")
    void shouldCreateContactObjWithArgs() {
        Contact eg2 = new Contact(1, "111555999", "email@gmail.com");

        assertEquals("email@gmail.com", eg2.emailAddress);
    }

}
