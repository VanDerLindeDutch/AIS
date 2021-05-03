package ru.FSPO.AIS.newmodels;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Placement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "placement_id")
    private Long placementId;
    private long surface;
    private long price;
    @Column(name = "image_path")
    private String imagePath;
    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;
    @ManyToMany(mappedBy = "placementSet", cascade=CascadeType.ALL)
    private Set<Service> serviceSet;
    @OneToMany(mappedBy = "placement", cascade=CascadeType.ALL)
    private Set<RequestToBcLink> requestsSet;
    @OneToMany(mappedBy = "placement", cascade=CascadeType.ALL)
    private Set<RentedPlacement> rentedPlacementSet;

}
