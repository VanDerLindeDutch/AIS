package ru.FSPO.AIS.newmodels;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bc_link")
@Data
@NoArgsConstructor
public class BcLink extends AbstractUser {

    @OneToMany(mappedBy = "bcLink", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<BusinessCenter> businessCenters;

    @OneToMany(mappedBy = "bcLink")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RequestToBcLink> requestToBcLinks;


    public BcLink(Long id) {
        super(id);
    }

    @Override
    @Transient
    public Role getRole() {
        return Role.LANDLORD;
    }


}
