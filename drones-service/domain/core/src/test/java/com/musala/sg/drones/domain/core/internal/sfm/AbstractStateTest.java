package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public abstract class AbstractStateTest {
    protected void expectedExecution(State expectedState, DroneFSM fms,Executable executable){
        assertDoesNotThrow(executable);
        DroneFSM expectedFSM = DroneFSM.of(fms.getDrone(), expectedState);
        verify(fms.getDrone()).updateState(expectedFSM);
    }
    protected void expectedThrows(Executable supplier) {
        assertThrows(UnsupportedOperationException.class, supplier);
    }
    protected Medication mockMedication() {
        return null;
    }

    protected abstract State getState();

    protected DroneFSM getDroneFSM() {
        return DroneFSM.of(spyDrone(), getState());
    }


    protected DroneImpl spyDrone() {
        return spy(new DroneImpl("", "", 500, 100, getState()));
    }


}
