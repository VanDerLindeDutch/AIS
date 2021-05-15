package ru.FSPO.AIS.newdao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.Floor;
import ru.FSPO.AIS.newmodels.Placement;
import ru.FSPO.AIS.newmodels.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface PlacementRepository  extends CrudRepository<Placement, Long> {



     @Query(value = "SELECT DISTINCT p FROM Placement p " +
             "LEFT JOIN FETCH p.floor " +
             "LEFT JOIN FETCH p.rentedPlacement " +
             "LEFT JOIN FETCH p.serviceSet " +
             "LEFT JOIN FETCH p.requestsSet r " +
             "WHERE p.floor.floorId = ?1")
     Set<Placement> findPlacementsByFloorId(long fId);


     default List<Placement> findPlacementsByServiceSetContains(EntityManager entityManager, Set<Service> set){
         javax.persistence.Query query = entityManager.createQuery(
                  "SELECT DISTINCT p FROM Placement p " +
                     "LEFT JOIN FETCH p.floor "  +
                     "LEFT JOIN FETCH p.rentedPlacement " +
                     "LEFT JOIN FETCH p.serviceSet " +
                     "LEFT JOIN FETCH p.requestsSet r " +
                     "LEFT JOIN p.serviceSet ps " +
                     "WHERE ps.serviceId in (:services)");
         query.setParameter("services", set.stream().map(Service::getServiceId).collect(Collectors.toSet()));
         return query.getResultList();
     }






}
