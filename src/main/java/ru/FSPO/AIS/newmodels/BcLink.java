package ru.FSPO.AIS.newmodels;


import lombok.*;
import ru.FSPO.AIS.models.Role;

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
    private Set<BusinessCenter> businessCenters;

    @OneToMany(mappedBy = "bcLink")
    @EqualsAndHashCode.Exclude
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
