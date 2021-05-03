package ru.FSPO.AIS.newmodels;


import lombok.Data;

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
    @Column(name = "is_checked")
    private boolean isCheked;
    @Column(name = "total_amount")
    private long totalAmount;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "r_id")
    private RenterLink renterLink;
    @ManyToOne
    @JoinColumn(name = "bc_link_id")
    private BcLink bcLink;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "pl_id")
    private Placement placement;
}
