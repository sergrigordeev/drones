package com.musala.sg.drones.adapters.jpa.drone.internal.mappers;

import com.musala.sg.drones.adapters.jpa.drone.internal.JpaDroneStateLog;
import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import com.musala.sg.drones.domain.usecases.api.ports.dto.DroneStatusDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface JpaDroneStateLogMapper {
    JpaDroneStateLog toEntity(BatteryLevelLogDto dto);

    BatteryLevelLogDto toDto(JpaDroneStateLog entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    JpaDroneStateLog partialUpdate(DroneStatusDto dto, @MappingTarget JpaDroneStateLog entity);


}