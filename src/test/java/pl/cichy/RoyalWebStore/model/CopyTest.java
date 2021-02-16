package pl.cichy.RoyalWebStore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopyTest {

    @Test
    @DisplayName("should create copy object with no-args")
    void shouldCreateCopyObj() {
        Copy eg1 = new Copy();

        assertNotEquals(null, eg1);
    }

}
