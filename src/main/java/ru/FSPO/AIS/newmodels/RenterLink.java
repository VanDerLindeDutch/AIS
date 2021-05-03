package ru.FSPO.AIS.newmodels;



import lombok.Data;
import ru.FSPO.AIS.models.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class RenterLink extends AbstractUser {


    @OneToMany(mappedBy = "renterLink", cascade=CascadeType.ALL)
    private Set<RequestToBcLink> requestsSet;

    @OneToMany(mappedBy = "renterLink", cascade=CascadeType.ALL)
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
                ", firstName='" + firstName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



}
