package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.Service;

public interface ServiceRepository  extends CrudRepository<Service, Long> {
}
