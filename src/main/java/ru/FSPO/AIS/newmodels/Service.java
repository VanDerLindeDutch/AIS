package ru.FSPO.AIS.newmodels;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;
    private String description;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "placement_service",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "placement_id")
    )
    private Set<Placement> placementSet;
}
