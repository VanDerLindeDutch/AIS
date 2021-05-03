package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.DataJpaTestEntity;
import ru.FSPO.AIS.newmodels.Placement;

public interface PlacementRepository  extends CrudRepository<Placement, Long> {
}
