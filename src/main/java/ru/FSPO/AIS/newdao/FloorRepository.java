package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.DataJpaTestEntity;
import ru.FSPO.AIS.newmodels.Floor;

public interface FloorRepository  extends CrudRepository<Floor, Long> {
}
