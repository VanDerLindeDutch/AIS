package ru.FSPO.AIS.newdao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.BcLink;
import ru.FSPO.AIS.newmodels.RequestToBcLink;

import java.util.Set;

public interface RequestToBcLinkRepository extends CrudRepository<RequestToBcLink, Long> {
    Set<RequestToBcLink> findRequestsToBcLinkByBcLink(BcLink bcLink);
    @Query(value = "SELECT count(r) FROM RequestToBcLink r " +
            "WHERE r.bcLink.id=?1 AND r.isCheked is false " +
            "GROUP BY r.totalAmount" )
    Long countRequestToBcLinkByBcLink(Long bcLinkId);

}
