package ru.FSPO.AIS.newmodels;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Floor floor;


    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "placement_service",
            joinColumns = @JoinColumn(name = "placement_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Service> serviceSet;

    @OneToMany(mappedBy = "placement", cascade = CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RequestToBcLink> requestsSet;

    @OneToOne(mappedBy = "placement", cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RentedPlacement rentedPlacement;

    public Placement(Long placementId) {
        this.placementId = placementId;
    }


}
