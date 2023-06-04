package com.musala.sg.drones.domain.core.internal;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DroneIdentityTest {

    @Test
    void that_throws_when_sn_is_incorrect() {
        assertThrows(NullPointerException.class, () -> new DroneIdentity(null, DroneIdentity.Model.CRUISERWEIGHT));
        assertThrows(IllegalStateException.class, () -> new DroneIdentity("", DroneIdentity.Model.CRUISERWEIGHT));
        assertThrows(IllegalStateException.class, () -> new DroneIdentity(" ", DroneIdentity.Model.CRUISERWEIGHT));
        String serialNumber = generateSN(101);
        assertThrows(IllegalStateException.class, () -> new DroneIdentity(serialNumber, DroneIdentity.Model.CRUISERWEIGHT));
    }

    @Test
    void that_drone_identity_instantiated() {
        assertDoesNotThrow(() -> new DroneIdentity("sn", DroneIdentity.Model.LIGHTWEIGHT));
        assertDoesNotThrow(() -> new DroneIdentity("sn", DroneIdentity.Model.MIDDLEWEIGHT));
        assertDoesNotThrow(() -> new DroneIdentity("sn", DroneIdentity.Model.HEAVYWEIGHT));
        assertDoesNotThrow(() -> new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT));
    }

    @Test
    void that_drone_identities_are_equal() {
        assertEquals(new DroneIdentity("sn", DroneIdentity.Model.LIGHTWEIGHT), new DroneIdentity("sn", DroneIdentity.Model.LIGHTWEIGHT));
    }

    @Test
    void that_drone_identities_are_not_equal() {
        assertNotEquals(new DroneIdentity("sn", DroneIdentity.Model.HEAVYWEIGHT), new DroneIdentity("sn", DroneIdentity.Model.LIGHTWEIGHT));
    }

    private String generateSN(int length) {
        return StringUtils.repeat("*", length);
    }

}