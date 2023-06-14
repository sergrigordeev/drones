package com.musala.sg.drones.adapters.jpa.drone.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(name = "battery_level_serial_number", columnList = "serialNumber", unique = true)})
@Getter
@Setter
public class JpaDroneStateLog {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    @Id
    private UUID id;

    @Column(nullable = false, updatable = false)
    private String serialNumber;

    @Column(nullable = false, updatable = false)
    Integer batteryLevel;
    @Column(nullable = false, updatable = false)
    Boolean success;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Version
    private int version;
}
