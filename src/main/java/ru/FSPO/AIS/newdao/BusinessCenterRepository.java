package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.BusinessCenter;
import ru.FSPO.AIS.newmodels.DataJpaTestEntity;

public interface BusinessCenterRepository  extends CrudRepository<BusinessCenter, Long> {
}
