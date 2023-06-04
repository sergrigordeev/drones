package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DroneFSMTest {

    @Nested
    class FactoryMethodTest {
        @Test
        void that_returns_idle_fsm() {
            check(IdleState.class, State.IDLE);
        }

        @Test
        void that_returns_loading_fsm() {
            check(LoadingState.class, State.LOADING);
        }

        @Test
        void that_returns_loaded_fsm() {
            check(LoadedState.class, State.LOADED);
        }

        @Test
        void that_returns_delivering_fsm() {
            check(DeliveringState.class, State.DELIVERING);
        }

        @Test
        void that_returns_delivered_fsm() {
            check(DeliveredState.class, State.DELIVERED);
        }

        @Test
        void that_returns_unloading_fsm() {
            check(UnloadingState.class, State.UNLOADING);
        }

        @Test
        void that_returns_unloaded_fsm() {
            check(UnloadedState.class, State.UNLOADED);
        }

        @Test
        void that_returns_returning_fsm() {
            check(ReturningState.class, State.RETURNING);
        }

        void check(Class clazz, State state) {
            DroneFSM fsm = DroneFSM.of(mockDrone(), state);
            assertNotNull(fsm);
            assertEquals(state, fsm.getState());
            assertInstanceOf(clazz, fsm);
        }

        private DroneImpl mockDrone() {
            DroneImpl mockDrone = Mockito.mock(DroneImpl.class);
            Mockito.doReturn(100).when(mockDrone).getBatteryLevel();
            return mockDrone;
        }
    }
}