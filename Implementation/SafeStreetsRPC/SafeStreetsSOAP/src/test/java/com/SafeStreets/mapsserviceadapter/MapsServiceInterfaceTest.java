package com.SafeStreets.mapsserviceadapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * It tests the methods of MapsServiceInterface
 */
class MapsServiceInterfaceTest {
    /**
     * Test that the factory method does not return null.
     */
    @Test
    void getInstance_notNull() {
        assertDoesNotThrow(MapsServiceInterface::getInstance);
        assertNotNull(MapsServiceInterface.getInstance());
    }
}