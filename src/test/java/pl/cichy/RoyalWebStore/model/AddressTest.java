package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    @DisplayName("should create address object with no-args")
    void shouldCreateAddressObj() {
        Address eg1 = new Address();

        assertNotEquals(null, eg1);
    }

    @Test
    @DisplayName("should create address object with args")
    void shouldCreateAddressObjWithArgs() {
        Address eg2 = new Address(1, "Kraków", "małopolskie", "32-511",
                "cala", "21");

        assertEquals("Kraków", eg2.getCity());
    }

}
