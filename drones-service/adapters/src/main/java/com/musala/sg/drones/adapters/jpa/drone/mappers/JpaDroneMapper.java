package com.musala.sg.drones.adapters.jpa.drone.mappers;

import com.musala.sg.drones.adapters.jpa.drone.JpaDrone;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = JpaCargoMapper.class)
public interface JpaDroneMapper {
    JpaDrone toEntity(DroneDto droneDto);

    DroneDto toDto(JpaDrone jpaDrone);

    List<DroneDto> toDtos(List<JpaDrone> drones);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    JpaDrone partialUpdate(DroneDto droneDto, @MappingTarget JpaDrone jpaDrone);

    @Mapping(target = "batteryLevel", source = "batteryLevel")
    DroneDto toDto(JpaDrone jpaDrone, int batteryLevel);
}