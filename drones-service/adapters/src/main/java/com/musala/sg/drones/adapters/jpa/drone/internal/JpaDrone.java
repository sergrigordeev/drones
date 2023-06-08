package com.musala.sg.drones.adapters.jpa.drone.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(name = "drone_serial_number", columnList = "serial_number", unique = true)})
@NamedEntityGraph(name = "Drone.cargos",
        attributeNodes = @NamedAttributeNode("cargos")
)
@Getter
@Setter
public class JpaDrone {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    @Id
    private UUID id;
    @Column(nullable = false, updatable = false, unique = true)
    private String serialNumber;
    @Column(nullable = false, updatable = false)
    private String model;
    private int maxWeight;
    private String state;
    @OneToMany(mappedBy = "drone")
    private List<JpaCargo> cargos;

    ////
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @Version
    private int version;

    @Override
    public int hashCode() {
        return Objects.hashCode(serialNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        JpaDrone other = (JpaDrone) obj;
        return Objects.equals(serialNumber, other.getSerialNumber());
    }
}
