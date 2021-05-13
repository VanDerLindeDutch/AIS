package ru.FSPO.AIS.newmodels;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;
    private String description;

    @ManyToMany(mappedBy = "serviceSet")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Placement> placementSet;

    public Service(Long serviceId) {
        this.serviceId = serviceId;
    }
}
