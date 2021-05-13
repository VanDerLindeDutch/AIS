package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.Floor;

import java.util.Set;

public interface FloorRepository  extends CrudRepository<Floor, Long> {
    Set<Floor> findFloorByBusinessCenterBcId(long bcId);
}
