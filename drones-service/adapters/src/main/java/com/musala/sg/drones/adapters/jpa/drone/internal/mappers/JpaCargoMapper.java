package com.musala.sg.drones.adapters.jpa.drone.internal.mappers;

import com.musala.sg.drones.adapters.jpa.drone.internal.JpaCargo;
import com.musala.sg.drones.domain.core.api.dto.CargoDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface JpaCargoMapper {
    JpaCargo toEntity(CargoDto cargoDto);

    CargoDto toDto(JpaCargo jpaCargo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    JpaCargo partialUpdate(CargoDto cargoDto, @MappingTarget JpaCargo jpaCargo);
}