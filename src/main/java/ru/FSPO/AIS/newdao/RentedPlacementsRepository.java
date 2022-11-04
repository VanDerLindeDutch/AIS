package ru.FSPO.AIS.newdao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.BcLink;
import ru.FSPO.AIS.newmodels.Floor;
import ru.FSPO.AIS.newmodels.RentedPlacement;
import ru.FSPO.AIS.newmodels.RenterLink;

import java.util.List;

public interface RentedPlacementsRepository extends CrudRepository<RentedPlacement, Long> {
    @Query("SELECT r FROM RentedPlacement r " +
            "JOIN fetch r.placement p " +
            "JOIN FETCH p.floor f " +
            "JOIN FETCH f.businessCenter b " +
            "WHERE r.renterLink = ?1")
    List<RentedPlacement> findRentedPlacementsByRenterLink(RenterLink renterLink);


    @Query("SELECT r FROM RentedPlacement r " +
            "JOIN fetch r.placement p " +
            "JOIN FETCH p.floor f " +
            "JOIN FETCH f.businessCenter b " +
            "WHERE r.placement.floor.businessCenter.bcLink = ?1")
    List<RentedPlacement> findRentedPlacementsByBcLink(BcLink bcLink);


    List<RentedPlacement> findRentedPlacementsByPlacement_Floor(Floor floor);
}