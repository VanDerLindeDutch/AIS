package ru.FSPO.AIS.newmodels;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "rented_placement")
@Data
public class RentedPlacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "placement_id")
    private Long rPlacementId;

    @Column(name = "total_amount")
    private long totalAmount;
    @Column(name = "start_of_rent")
    private Date startOfRent;
    @Column(name = "end_of_rent")
    private Date endOfRent;

    @OneToOne
    @MapsId
    @JoinColumn(name = "placement_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Placement placement;

    @ManyToOne
    @JoinColumn(name = "r_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RenterLink renterLink;

}
