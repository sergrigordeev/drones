package com.musala.sg.drones.adapters.jpa.drone.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(name = "battery_level_serial_number", columnList = "serial_number", unique = true)})
@Getter
@Setter
public class JpaBatteryLevelLog {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    @Id
    private UUID id;

    @Column(nullable = false, updatable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false, updatable = false, unique = true)
    int batteryLevel;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Version
    private int version;
}
