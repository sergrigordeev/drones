package com.musala.sg.drones.adapters.jpa.drone.internal.mappers;

import com.musala.sg.drones.adapters.jpa.drone.internal.JpaDroneStateLog;
import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import com.musala.sg.drones.domain.usecases.api.ports.dto.DroneStatusDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface JpaDroneStateLogMapper {
    JpaDroneStateLog toEntity(BatteryLevelLogDto dto);

    @Mapping(target = "lastCheckDateTime", source = "createdAt")
    BatteryLevelLogDto toDto(JpaDroneStateLog entity);

    default OffsetDateTime map(LocalDateTime value) {
        return value.atOffset(ZoneOffset.UTC);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    JpaDroneStateLog partialUpdate(DroneStatusDto dto, @MappingTarget JpaDroneStateLog entity);


}