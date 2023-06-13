package com.musala.sg.drones.container.config;

import com.musala.sg.drones.domain.core.api.DroneFactory;
import com.musala.sg.drones.domain.core.internal.DroneFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    DroneFactory droneFactory() {
        return new DroneFactoryImpl();
    }
}
