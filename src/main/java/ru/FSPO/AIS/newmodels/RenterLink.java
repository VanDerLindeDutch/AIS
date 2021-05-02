package ru.FSPO.AIS.newmodels;



import ru.FSPO.AIS.models.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class RenterLink extends AbstractUser {

    private long companyId;

    @Override
    @Transient
    public Role getRole() {
        return Role.RENTER;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
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
