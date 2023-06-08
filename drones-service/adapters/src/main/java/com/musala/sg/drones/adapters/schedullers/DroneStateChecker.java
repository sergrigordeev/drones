package com.musala.sg.drones.adapters.schedullers;

import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.drones.realtime.check.DroneRuntimeStatusCheckUsecase;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class DroneStateChecker {
    private final DroneRuntimeStatusCheckUsecase usecase;
    private final FindDronesPort findDronesPort;

    @Scheduled(fixedDelayString = "${drones.checker.fixedDelay.in.milliseconds:60000}", initialDelayString = "${drones.checker.initialDelay.in.milliseconds:10000}")
    void check() {
        List<DroneDto> drones = findDronesPort.findAll();
        log.info("found {} drones. start update", drones.size());
        for (DroneDto drone : drones) {
            log.info("--> check {}", drone.getSerialNumber());
            try {
                usecase.execute(new DroneSearchQuery(drone.getSerialNumber()));
                log.info("--> checked");
            } catch (Exception e) {
                log.error("Cant retrieve  info  for {}", drone.getSerialNumber());
            }
        }

    }
}
