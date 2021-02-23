package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("should create order object with no-args")
    void shouldCreateOrderObj() {
        Order eg1 = new Order();

        assertNotEquals(null, eg1);
    }

}
