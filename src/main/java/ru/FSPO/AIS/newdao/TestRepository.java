package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.DataJpaTestEntity;

public interface TestRepository  extends CrudRepository<DataJpaTestEntity, Long> {
}
