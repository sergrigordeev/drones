package com.musala.sg.drones.adapters.jpa.drone.internal;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface JpaDroneRepository extends JpaRepository<JpaDrone, UUID> {
    @EntityGraph(value = "Drone.cargos")
    Optional<JpaDrone> findBySerialNumber(String serialNumber);

    @EntityGraph(value = "Drone.cargos")
    List<JpaDrone> findAllByState(String idle);
}
