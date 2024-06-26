package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoadingStateTest extends AbstractStateTest {

    @Test
    void that_behavior_is_correct_for_idle_command() {
        expectedThrows(getDroneFSM()::idle);
    }

    @Test
    void that_behavior_is_correct_for_start_loading_command() {
        DroneFSM state = getDroneFSM();
        expectedExecution(State.LOADING, state, state::startLoading);
    }

    @Test
    void that_behavior_is_correct_for_load_command() {
        DroneFSM state = getDroneFSM();
        Medication medication = mockMedication();
        expectedExecution(State.LOADING, state, () -> state.load(medication));
        verify(state.getDrone()).loadToCargoHold(medication);
    }


    @Test
    void that_behavior_is_correct_for_end_load_command() {
        DroneFSM state = getDroneFSM();
        expectedExecution(State.LOADED, state, state::endLoading);
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
        expectedThrows(getDroneFSM()::startCharging);
    }

    @Test
    void that_behavior_is_correct_for_end_charging_command() {
        expectedThrows(getDroneFSM()::endCharging);
    }

    @Override
    protected State getState() {
        return State.LOADING;
    }
}