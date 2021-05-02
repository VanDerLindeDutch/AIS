package ru.FSPO.AIS.newmodels;


import ru.FSPO.AIS.models.Role;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.Set;

@Entity
public class BcLink extends AbstractUser {

    @OneToMany
    private Set<BusinessCenter> businessCenters;


    public Set<BusinessCenter> getBusinessCenters() {
        return businessCenters;
    }

    public void setBusinessCenters(Set<BusinessCenter> businessCenters) {
        this.businessCenters = businessCenters;
    }

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
