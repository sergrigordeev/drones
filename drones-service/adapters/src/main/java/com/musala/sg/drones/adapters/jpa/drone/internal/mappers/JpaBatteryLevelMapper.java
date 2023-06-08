package com.musala.sg.drones.adapters.jpa.drone.internal.mappers;

import com.musala.sg.drones.adapters.jpa.drone.internal.JpaBatteryLevelLog;
import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface JpaBatteryLevelMapper {
    JpaBatteryLevelLog toEntity(BatteryLevelLogDto dto);

    BatteryLevelLogDto toDto(JpaBatteryLevelLog entity);


}