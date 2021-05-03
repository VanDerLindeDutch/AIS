package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.DataJpaTestEntity;
import ru.FSPO.AIS.newmodels.RentedPlacement;

public interface RentedPlacementsRepository  extends CrudRepository<RentedPlacement, Long> {
}
