package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    @DisplayName("should create employee object with no-args")
    void shouldCreateEmployeeObj() {
        Employee eg1 = new Employee();

        assertNotEquals(null, eg1);
    }

    @Test
    @DisplayName("should create employee object with args")
    void shouldCreateEmployeeObjWithArgs() {
        Employee eg2 = new Employee(1, "sky", "labamba", "Przemek",
                "C", "buisness");

        assertEquals("labamba", eg2.getPassword());
    }

}
