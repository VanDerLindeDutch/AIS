package ru.FSPO.AIS.newmodels;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "rented_placements")
@Data
public class RentedPlacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_placement_id")
    private Long rPlacementId;
    @Column(name = "total_amount")
    private long totalAmount;
    @Column(name = "start_of_rent")
    private Date startOfRent;
    @Column(name = "end_of_rent")
    private Date endOfRent;
    @ManyToOne
    @JoinColumn(name = "pl_id")
    private Placement placement;
    @ManyToOne
    @JoinColumn(name = "r_id")
    private RenterLink renterLink;

}
