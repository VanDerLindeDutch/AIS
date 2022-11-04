package ru.FSPO.AIS.newmodels;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "rented_placement")
@Data
public class RentedPlacement {

    @Id
    @Column(name = "placement_id")
    private Long placementId;

    @Column(name = "total_amount")
    private long totalAmount;
    @Column(name = "start_of_rent")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date startOfRent;
    @Column(name = "end_of_rent")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date endOfRent;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
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
