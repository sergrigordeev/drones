package com.musala.sg.drones.domain.core.internal;

import com.musala.sg.drones.domain.core.api.Cargo;
import com.musala.sg.drones.domain.core.api.exceptions.CargoLoadException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CargoHoldTest {

    @Nested
    class SelfCheckTest {
        @Test
        void that_throws_when_max_weight_is_incorrect() {
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new CargoHold(-1));
            assertEquals("Max weight can not be less than 0, but it is -1", exception.getMessage());
        }

        @Test
        void that_throws_when_cargo_list_is_null() {
            assertThrows(NullPointerException.class, () -> new CargoHold(0, null));
        }

        @Test
        void that_throws_when_cargos_weight_gt_than_max_weight() {
            List<Cargo> cargos = List.of(mockCargo(2));
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new CargoHold(1, cargos));
            assertEquals("Total weight of cargos is more than max weight.Max {}; Total{}".formatted(1, 2), exception.getMessage());
        }

        @Test
        void that_cargo_hold_instantiated() {
            assertDoesNotThrow(() -> new CargoHold(2, List.of(mockCargo(2))));
            assertDoesNotThrow(() -> new CargoHold(5, List.of(mockCargo(2), mockCargo(2))));
            assertDoesNotThrow(() -> new CargoHold(5, List.of(mockCargo(2), mockCargo(3))));
        }
    }

    @Nested
    class LoadTest {
        @Test
        void that_throws_when_cargo_is_null() {
            CargoHold cargoHold = new CargoHold(100);
            assertThrows(NullPointerException.class, () -> cargoHold.load(null));
        }

        @Test
        void that_throws_when_cargo_hold_has_not_enough_available_weight() {
            CargoHold cargoHold = new CargoHold(100);
            Cargo cargos = mockCargo(101);
            assertThrows(CargoLoadException.class, () -> cargoHold.load(cargos));
        }

        @Test
        void that_add_cargos_and() {
            CargoHold cargoHold = new CargoHold(100);
            for (int i = 1; i <= 100; ++i) {
                cargoHold.load(mockCargo(1));
                assertEquals(100 - i, cargoHold.availableWeight());
                assertEquals(i, cargoHold.totalWeight());
                assertEquals(i, cargoHold.totalCargo());
            }
            assertEquals(0, cargoHold.availableWeight());
            assertEquals(100, cargoHold.totalWeight());
            assertEquals(100, cargoHold.totalCargo());
        }
    }


    @Test
    void unloadAll() {
        CargoHold cargoHold = new CargoHold(5, List.of(mockCargo(2), mockCargo(2)));
        cargoHold.unloadAll();

        assertEquals(5, cargoHold.availableWeight());
        assertEquals(0, cargoHold.totalCargo());
    }

    private Cargo mockCargo(int i) {
        return () -> i;
    }
}