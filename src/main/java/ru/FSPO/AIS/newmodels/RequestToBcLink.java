package ru.FSPO.AIS.newmodels;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "request_to_bc_link")
@Data
public class RequestToBcLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "expiration_date")
    private Date expirationDate;
    @Column(name = "start_of_rent")
    private Date startOfRent;
    @Column(name = "end_of_rent")
    private Date endOfRent;
    @Column(name = "is_checked_by_bc_link")
    private boolean isCheckedByBcLink;
    @Column(name = "show_to_renter")
    private boolean showToRenter;
    @Column(name = "total_amount")
    private long totalAmount;

    @ManyToOne
    @JoinColumn(name = "r_id")
    private RenterLink renterLink;

    @ManyToOne
    @JoinColumn(name = "bc_link_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BcLink bcLink;

    @ManyToOne
    @JoinColumn(name = "pl_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Placement placement;
}
