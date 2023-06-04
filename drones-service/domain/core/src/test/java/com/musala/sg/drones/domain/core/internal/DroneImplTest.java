package com.musala.sg.drones.domain.core.internal;

import com.musala.sg.drones.domain.core.api.Cargo;
import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.api.exceptions.CargoLoadException;
import com.musala.sg.drones.domain.core.api.exceptions.LoadingStateException;
import com.musala.sg.drones.domain.core.internal.sfm.DroneFSM;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DroneImplTest {

    @Nested
    class SelfCheckTest {
        @Test
        void that_throws_when_some_params_are_null() {
            assertThrows(NullPointerException.class, () -> new DroneImpl(null, mock(CargoHold.class), mock(Battery.class), State.IDLE));
            assertThrows(NullPointerException.class, () -> new DroneImpl(mock(DroneIdentity.class), null, mock(Battery.class), State.IDLE));
            assertThrows(NullPointerException.class, () -> new DroneImpl(mock(DroneIdentity.class), mock(CargoHold.class), null, State.IDLE));
            assertThrows(NullPointerException.class, () -> new DroneImpl(mock(DroneIdentity.class), mock(CargoHold.class), mock(Battery.class), null));
        }

        @Test
        void that_drone_instantiated() {
            assertDoesNotThrow(() -> new DroneImpl(mock(DroneIdentity.class), mock(CargoHold.class), mock(Battery.class), State.IDLE));
            assertDoesNotThrow(() -> new DroneImpl(mock(DroneIdentity.class), mock(CargoHold.class), mock(Battery.class), State.RETURNING));
        }
    }

    @Nested
    class InstantiateTest {
        @Test
        void that_drone_has_been_created_with_proper_fsm() {
            for (State state : State.values()) {
                check(state);
            }
        }

        void check(State state) {
            DroneImpl drone = new DroneImpl(mock(DroneIdentity.class), mock(CargoHold.class), new Battery(100), state);
            assertEquals(DroneFSM.of(drone, state), drone.getFSM());
        }
    }

    @Nested
    class UpdateStateTest {
        @Test
        void that_throws_when_fsm_is_null() {
            DroneImpl drone = new DroneImpl(mock(DroneIdentity.class), mock(CargoHold.class), new Battery(100), State.IDLE);
            assertThrows(NullPointerException.class, () -> drone.updateState(null));
        }
    }

    @Nested
    class EqualityTest {

        @Test
        void that_equals() {
            DroneIdentity droneIdentity = new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT);
            DroneImpl drone1 = new DroneImpl(droneIdentity, new CargoHold(500), new Battery(100), State.IDLE);
            DroneImpl drone2 = new DroneImpl(droneIdentity, new CargoHold(345), new Battery(67), State.RETURNING);

            assertEquals(drone1, drone2);
        }

        @Test
        void that_not_equals() {
            CargoHold cargoHold = new CargoHold(500);
            Battery battery = new Battery(100);
            DroneImpl drone1 = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.IDLE);
            DroneImpl drone2 = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.LIGHTWEIGHT), cargoHold, battery, State.IDLE);

            assertNotEquals(drone1, drone2);
        }
    }

    @Nested
    class LoadTest {
        @Test
        void that_drone_doesnt_change_state_to_loading_when_battery_lvl_is_low() {
            CargoHold cargoHold = new CargoHold(500);
            Battery battery = new Battery(23);
            DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.IDLE);
            assertThrows(LoadingStateException.class, () -> drone.startLoading());
        }

        @Test
        void that_drone_change_state_to_loading_when_battery_lvl_is_ok() {
            CargoHold cargoHold = new CargoHold(500);
            Battery battery = new Battery(25);
            DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.IDLE);
            assertDoesNotThrow(() -> drone.startLoading());
            assertEquals(State.LOADING, drone.getFSM().getState());
        }

        @Test
        void that_medication_could_not_be_loaded_when_drone_has_wrong_state() {
            CargoHold cargoHold = new CargoHold(500);
            Battery battery = new Battery(100);
            DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.IDLE);
            for (State state : State.values()) {
                if (state == State.LOADING) continue;
                drone.updateState(DroneFSM.of(drone, state));
                assertThrows(UnsupportedOperationException.class, () -> drone.load(mock(Medication.class)));
            }
        }

        @Test
        void that_medication_could_not_be_loaded_when_cargo_hold_has_no_space() {
            CargoHold cargoHold = new CargoHold(500);
            Battery battery = new Battery(100);
            DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.LOADING);
            Medication medication = getMedication(501);
            assertThrows(CargoLoadException.class, () -> drone.load(medication));
        }


        @Test
        void that_medication_could_not_be_loaded_when_cargo_is_null() {
            CargoHold cargoHold = new CargoHold(500);
            Battery battery = new Battery(100);
            DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.LOADING);
            assertThrows(NullPointerException.class, () -> drone.load(null));
        }

        @Test
        void that_medication_could_be_loaded() {
            CargoHold cargoHold = new CargoHold(100);
            Battery battery = new Battery(100);
            DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.LOADING);
            for (int i = 1; i <= 100; ++i) {
                assertTrue(drone.couldLoadCargo());
                assertDoesNotThrow(() -> drone.load(getMedication(1)));
            }
            assertFalse(drone.couldLoadCargo());
        }

        @Test
        void that_drone_change_state_to_loaded() {
            CargoHold cargoHold = new CargoHold(500);
            Battery battery = new Battery(25);
            DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.LOADING);
            assertDoesNotThrow(() -> drone.endLoading());
            assertEquals(State.LOADED, drone.getFSM().getState());
        }

    }

    @Nested
    class UnloadTest {

        @Test
        void that_drone_change_state_to_unloading() {
            CargoHold cargoHold = new CargoHold(500);
            Battery battery = new Battery(25);
            DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.DELIVERED);
            assertDoesNotThrow(() -> drone.startUnloading());
            assertEquals(State.UNLOADING, drone.getFSM().getState());
        }

        @Test
        void that_drone_unload_all() {
            CargoHold spyCargoHolder = spy(new CargoHold(500));
            Battery battery = new Battery(25);
            DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), spyCargoHolder, battery, State.UNLOADING);
            assertDoesNotThrow(() -> drone.unloadAll());
            verify(spyCargoHolder).unloadAll();
            assertEquals(State.UNLOADED, drone.getFSM().getState());
        }
    }

    @Test
    void that_start_delivery_works() {
        CargoHold cargoHold = new CargoHold(500);
        Battery battery = new Battery(25);
        DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.LOADED);
        drone.startDelivery();
        assertEquals(State.DELIVERING, drone.getFSM().getState());
    }

    @Test
    void that_get_cargos_returns_all_cargos() {
        CargoHold cargoHold = new CargoHold(500, List.of(getMedication(1), getMedication(2)));
        Battery battery = new Battery(25);
        DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.LOADED);
        List<Cargo> cargos = drone.getCargos();
        assertEquals(2, cargos.size());
    }

    @Test
    void that_get_battery_lvl_return_value() {
        CargoHold cargoHold = new CargoHold(500);
        Battery battery = new Battery(25);
        DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.LOADED);
        int lvl = drone.getBatteryLevel();
        assertEquals(25, lvl);
    }

    @Test
    void that_get_max_weight_return_value() {
        CargoHold cargoHold = new CargoHold(500);
        Battery battery = new Battery(25);
        DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.LOADED);
        assertEquals(cargoHold.getMaxWeight(), drone.getMaxWeight());
    }

    @Test
    void that_get_available_return_value() {
        CargoHold cargoHold = new CargoHold(500, List.of(getMedication(456)));
        Battery battery = new Battery(25);
        DroneImpl drone = new DroneImpl(new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT), cargoHold, battery, State.LOADED);
        assertEquals(cargoHold.availableWeight(), drone.getAvailableWeight());
    }

    @Test
    void that_get_identity_return_value() {
        CargoHold cargoHold = new CargoHold(500, List.of(getMedication(456)));
        Battery battery = new Battery(25);
        DroneIdentity identity = new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT);
        DroneImpl drone = new DroneImpl(identity, cargoHold, battery, State.LOADED);
        assertEquals(identity, drone.getIdentity());
    }

    private Medication getMedication(int weight) {
        Medication medication = new Medication("name", "CODE", weight, "");
        return medication;
    }
}