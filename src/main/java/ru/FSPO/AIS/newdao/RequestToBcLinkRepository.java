package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.BcLink;
import ru.FSPO.AIS.newmodels.RequestToBcLink;

import java.util.Set;

public interface RequestToBcLinkRepository extends CrudRepository<RequestToBcLink, Long> {
    Set<RequestToBcLink> findRequestsToBcLinkByBcLink(BcLink bcLink);
    Long countRequestToBcLinkByBcLinkAndIsChekedIsTrue(BcLink bcLink);
}
