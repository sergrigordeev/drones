package com.musala.sg.drones.adapters.jpa.drone;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface JpaCargoRepository extends JpaRepository<JpaCargo, UUID> {


}
