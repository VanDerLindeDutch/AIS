package ru.FSPO.AIS.newdao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.BusinessCenter;

import java.util.Optional;

public interface BusinessCenterRepository extends CrudRepository<BusinessCenter, Long> {
    @Override
    @Query(
            value = "SELECT b FROM BusinessCenter b " +
                    "JOIN FETCH b.bcLink " +
                    "LEFT JOIN FETCH b.floors " +
                    "WHERE b.bcId = ?1"
    )
    Optional<BusinessCenter> findById(Long aLong);
}
