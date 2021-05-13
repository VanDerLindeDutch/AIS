package ru.FSPO.AIS.newdao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.BcLink;
import ru.FSPO.AIS.newmodels.BusinessCenter;

import java.util.Optional;

public interface BcLinkRepository  extends CrudRepository<BcLink, Long> {
    Optional<BcLink> findBcLinkByLogin(String login);

    @Query(value = "SELECT b.id FROM BcLink b " +
            "JOIN b.businessCenters bc " +
            "JOIN bc.floors f " +
            "JOIN f.placements p ON p.placementId = ?1")
    Optional<Long> findBcLinkByPlacementID(Long plId);

    Optional<BcLink> findBcLinkByBusinessCenters(BusinessCenter businessCenter);
}
