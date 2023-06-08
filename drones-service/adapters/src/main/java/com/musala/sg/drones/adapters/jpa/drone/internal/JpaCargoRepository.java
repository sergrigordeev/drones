package com.musala.sg.drones.adapters.jpa.drone.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaCargoRepository extends JpaRepository<JpaCargo, UUID> {


}
