package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IdleStateTest {

    @Test
    void that_behavior_is_correct_for_idle_command() {
        DroneFSM state = getState();
        DroneImpl drone = state.getDrone();

        assertDoesNotThrow(() -> state.idle());

        DroneImpl spyDrone = state.getDrone();

        DroneFSM expectedState = DroneFSM.of(drone, State.IDLE);
        verify(spyDrone).updateState(expectedState);
    }

    @Test
    void that_behavior_is_correct_for_start_loading_command() {
        DroneFSM state = getState();
        DroneImpl drone = state.getDrone();

        assertDoesNotThrow(() -> state.startLoading());

        DroneFSM expectedState = DroneFSM.of(drone, State.LOADING);
        verify(drone).updateState(expectedState);
    }

    @Test
    void that_behavior_is_correct_for_load_command() {
        DroneFSM state = getState();
        Medication medication = mockMedication();

        assertThrows(UnsupportedOperationException.class, () -> state.load(medication));
    }

    @Test
    void that_behavior_is_correct_for_end_load_command() {
        DroneFSM state = getState();
        assertThrows(UnsupportedOperationException.class, () -> state.endLoading());
    }

    @Test
    void that_behavior_is_correct_for_end_start_delivery_command() {
        DroneFSM state = getState();
        assertThrows(UnsupportedOperationException.class, () -> state.startDelivery());
    }

    @Test
    void that_behavior_is_correct_for_end_delivered_command() {
        DroneFSM state = getState();
        assertThrows(UnsupportedOperationException.class, () -> state.delivered());
    }

    @Test
    void that_behavior_is_correct_for_end_start_unload_command() {
        DroneFSM state = getState();
        assertThrows(UnsupportedOperationException.class, () -> state.startUnloading());
    }

    @Test
    void that_behavior_is_correct_for_end_unload_all_command() {
        DroneFSM state = getState();
        assertThrows(UnsupportedOperationException.class, () -> state.unloadAll());
    }

    @Test
    void that_behavior_is_correct_for_return_to_base_command() {
        DroneFSM state = getState();
        DroneImpl drone = state.getDrone();

        assertDoesNotThrow(() -> state.returnToBase());

        DroneFSM expectedState = DroneFSM.of(drone, State.RETURNING);
        verify(drone).updateState(expectedState);
    }

    @Test
    void that_behavior_is_correct_for_start_charge_command() {
        DroneFSM state = getState();
        DroneImpl drone = state.getDrone();

        assertDoesNotThrow(() -> state.startCharging());

        DroneFSM expectedState = DroneFSM.of(drone, State.CHARGING);
        verify(drone).updateState(expectedState);
    }

    @Test
    void that_behavior_is_correct_for_end_charging_command() {
        DroneFSM state = getState();
        assertThrows(UnsupportedOperationException.class, () -> state.endCharging());
    }

    private Medication mockMedication() {
        return null;
    }

    DroneFSM getState() {
        return DroneFSM.of(spyDrone(), State.IDLE);
    }

    DroneImpl spyDrone() {
        return spy(new DroneImpl("", "", 500, 100, State.IDLE));
    }
}