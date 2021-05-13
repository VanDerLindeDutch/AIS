package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.Floor;
import ru.FSPO.AIS.newmodels.RentedPlacement;
import ru.FSPO.AIS.newmodels.RenterLink;

import java.util.List;

public interface RentedPlacementsRepository  extends CrudRepository<RentedPlacement, Long> {
    List<RentedPlacement> findRentedPlacementsByRenterLink(RenterLink renterLink);
    List<RentedPlacement> findRentedPlacementsByPlacement_Floor(Floor floor);
}