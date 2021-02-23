package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CustomerTest {

    @Test
    @DisplayName("should create customer object with no-args")
    void shouldCreateCustomerObj() {
        Customer eg1 = new Customer();

        assertNotEquals(null, eg1);
    }

    @Test
    @DisplayName("should create customer object with args")
    void shouldCreateCustomerObjWithArgs() {
        Customer eg2 = new Customer(1, "sky", "labamba", "Przemek",
                "C", "buisness");

        assertEquals("sky", eg2.getLogin());
    }

}
