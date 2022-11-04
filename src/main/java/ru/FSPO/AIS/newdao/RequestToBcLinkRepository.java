package ru.FSPO.AIS.newdao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.BcLink;
import ru.FSPO.AIS.newmodels.RenterLink;
import ru.FSPO.AIS.newmodels.RequestToBcLink;

import java.util.Set;

public interface RequestToBcLinkRepository extends CrudRepository<RequestToBcLink, Long> {


    Set<RequestToBcLink> findRequestsToBcLinkByBcLink(BcLink bcLink);

    Set<RequestToBcLink> findRequestToBcLinkByRenterLinkAndShowToRenterIsTrue(RenterLink renterLink);

    @Query(value = "SELECT count(r) FROM RequestToBcLink r " +
            "WHERE r.bcLink=?1 AND r.isCheckedByBcLink is false " +
            "GROUP BY r.totalAmount")
    Long countRequestToBcLinkByBcLink(BcLink bcLink);

    @Query(value = "SELECT count(r) FROM RequestToBcLink r " +
            "WHERE r.renterLink=?1 AND r.showToRenter is true " +
            "GROUP BY r.totalAmount")
    Long countRequestToBcLinkByRenterLink(RenterLink renterLink);

}
