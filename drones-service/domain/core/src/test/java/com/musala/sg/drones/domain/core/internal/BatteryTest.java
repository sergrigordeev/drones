package com.musala.sg.drones.domain.core.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatteryTest {
    @Test
    void that_throws_when_battery_capacity_is_incorrect() {
        assertThrows(IllegalStateException.class, () -> new Battery(-100));
        assertThrows(IllegalStateException.class, () -> new Battery(101));
        assertThrows(IllegalStateException.class, () -> new Battery(-1));
    }

    @Test
    void that_drone_identity_instantiated() {
        assertDoesNotThrow(() -> new Battery(100));
        assertDoesNotThrow(() -> new Battery(0));
        assertDoesNotThrow(() -> new Battery(50));
    }

    @Test
    void that_update_capacity_throws_when_new_capacity_not_in_rage() {
        Battery battery = new Battery(100);
        assertThrows(IllegalStateException.class, () -> battery.updateBatteryLevel(-100));
        assertThrows(IllegalStateException.class, () -> battery.updateBatteryLevel(101));
    }

    @Test
    void that_update_capacity_works() {
        Battery battery = new Battery(100);
        for (int i = 0; i <= 100; ++i) {
            int newCapacity = i;
            assertDoesNotThrow(() -> battery.updateBatteryLevel(newCapacity));
            assertEquals(i, battery.getBatteryLevel());
        }
    }
}