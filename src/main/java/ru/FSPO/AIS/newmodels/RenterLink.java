package ru.FSPO.AIS.newmodels;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.FSPO.AIS.models.Role;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class RenterLink extends AbstractUser {


    @OneToMany(mappedBy = "renterLink", cascade=CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    private Set<RequestToBcLink> requestsSet;

    @OneToMany(mappedBy = "renterLink", cascade=CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<RentedPlacement> rentedPlacementSet;

    @Override
    @Transient
    public Role getRole() {
        return Role.RENTER;
    }


    public RenterLink(Long id) {
        super(id);
    }
}
