package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChargingStateTest extends AbstractStateTest {

    @Test
    void that_behavior_is_correct_for_idle_command() {
        expectedThrows(getDroneFSM()::idle);
    }


    @Test
    void that_behavior_is_correct_for_start_loading_command() {
        expectedThrows(getDroneFSM()::startLoading);
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
        expectedThrows(getDroneFSM()::returnToBase);
    }

    @Test
    void that_behavior_is_correct_for_start_charge_command() {
        DroneFSM state = getDroneFSM();
        expectedExecution(State.CHARGING, state, state::startCharging);
        verify(state.getDrone()).startCharging();
    }

    @Test
    void that_behavior_is_correct_for_end_charging_command() {
        DroneFSM state = getDroneFSM();
        expectedExecution(State.IDLE, state, state::endCharging);
        verify(state.getDrone()).endCharging();
    }

    @Override
    protected State getState() {
        return State.CHARGING;
    }
}