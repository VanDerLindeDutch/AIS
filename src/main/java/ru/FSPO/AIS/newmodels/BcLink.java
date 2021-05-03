package ru.FSPO.AIS.newmodels;


import lombok.Data;
import ru.FSPO.AIS.models.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bc_link")
@Data
public class BcLink extends AbstractUser {

    @OneToMany(mappedBy = "bcLink")
    private Set<BusinessCenter> businessCenters;

    @OneToMany(mappedBy = "bcLink")
    private Set<RequestToBcLink> requestToBcLinks;


    @Override
    @Transient
    public Role getRole() {
        return Role.LANDLORD;
    }

    @Override
    public String toString() {
        return "BcLink{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
