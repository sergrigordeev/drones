package com.musala.sg.drones.adapters.jpa.drone.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class JpaCargo {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "drone_id", nullable = false)
    private JpaDrone drone;

    private String name;
    private String code;
    private int weight;
    private String imageUrl;
    //TODO: add cargo status LOADED, DELIVERED etc... and update it during load|unload operation
}
