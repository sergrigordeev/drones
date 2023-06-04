package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.Battery;
import com.musala.sg.drones.domain.core.internal.CargoHold;
import com.musala.sg.drones.domain.core.internal.DroneIdentity;
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
        return new Medication("name","CODE",1,"");
    }

    protected abstract State getState();

    protected DroneFSM getDroneFSM() {
        return DroneFSM.of(spyDrone(), getState());
    }


    protected DroneImpl spyDrone() {
        return spy(new DroneImpl(new DroneIdentity("1", DroneIdentity.Model.LIGHTWEIGHT), new CargoHold(100), new Battery(100), getState()));
    }
    protected DroneFSM getDroneFSMWithSpecificBatteryLevel(int batteryLevel) {
        return DroneFSM.of(spyDroneWithSpecificBatteryLevel(batteryLevel), getState());
    }
    protected DroneImpl spyDroneWithSpecificBatteryLevel(int batteryLevel) {
        return spy(new DroneImpl(
                new DroneIdentity("1", DroneIdentity.Model.LIGHTWEIGHT),
                new CargoHold(100),
                new Battery(batteryLevel),
                getState()));

    }
}
