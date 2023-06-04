package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.api.exceptions.LoadingStateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IdleStateTest extends AbstractStateTest {

    @Test
    void that_behavior_is_correct_for_idle_command() {
        DroneFSM state = getDroneFSM();
        expectedExecution(State.IDLE, state, state::idle);
    }


    @Test
    void that_behavior_is_correct_for_start_loading_command() {
        DroneFSM state = getDroneFSM();
        expectedExecution(State.LOADING, state, state::startLoading);
    }
    @Test
    void that_behavior_is_correct_for_start_loading_command_drone_battery_level_low() {
        DroneFSM state = getDroneFSMWithSpecificBatteryLevel(23);
        LoadingStateException loadingStateException = assertThrows(LoadingStateException.class, state::startLoading);
        assertEquals("Battery Level is to low THRESHOLD is 25, but current is 23",loadingStateException.getMessage());
    }



    @Test
    void that_behavior_is_correct_for_load_command() {
        expectedThrows(() -> getDroneFSM().load(mockMedication()));
    }

    @Test
    void that_behavior_is_correct_for_end_load_command() {
        expectedThrows(getDroneFSM()::endLoading);
    }

    @Test
    void that_behavior_is_correct_for_start_delivery_command() {
        expectedThrows(getDroneFSM()::startDelivery);
    }

    @Test
    void that_behavior_is_correct_for_end_delivered_command() {
        expectedThrows(getDroneFSM()::endDelivery);
    }

    @Test
    void that_behavior_is_correct_for_start_unload_command() {
        expectedThrows(getDroneFSM()::startUnloading);
    }

    @Test
    void that_behavior_is_correct_for_unload_all_command() {
        expectedThrows(getDroneFSM()::unloadAll);
    }

    @Test
    void that_behavior_is_correct_for_return_to_base_command() {
        DroneFSM state = getDroneFSM();
        expectedExecution(State.RETURNING, state, state::returnToBase);
    }

    @Test
    void that_behavior_is_correct_for_start_charge_command() {
        DroneFSM state = getDroneFSM();
        expectedExecution(State.CHARGING, state, state::startCharging);
    }

    @Test
    void that_behavior_is_correct_for_end_charging_command() {
        expectedThrows(getDroneFSM()::endCharging);
    }

    @Override
    protected State getState() {
        return State.IDLE;
    }
}