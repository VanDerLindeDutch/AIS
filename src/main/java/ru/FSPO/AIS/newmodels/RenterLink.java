package ru.FSPO.AIS.newmodels;



import lombok.*;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RenterLink extends AbstractUser {


    @OneToMany(mappedBy = "renterLink", cascade=CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RequestToBcLink> requestsSet;

    @OneToMany(mappedBy = "renterLink", cascade=CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RentedPlacement> rentedPlacementSet;

    @Override
    @Transient
    public Role getRole() {
        return Role.RENTER;
    }

    @Override
    public String toString() {
        return "RenterLink{" +
                "id=" + id +
                '}';
    }

    public RenterLink(Long id) {
        super(id);
    }
}
