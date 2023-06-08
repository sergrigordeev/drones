package com.musala.sg.drones.adapters.jpa.drone.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaDroneStateRepository extends JpaRepository<JpaDroneStateLog, UUID> {

    Optional<JpaDroneStateLog> findFirstBySerialNumberOrderByCreatedAtDesc(String serialNumber);
}
