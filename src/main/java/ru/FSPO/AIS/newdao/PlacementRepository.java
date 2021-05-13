package ru.FSPO.AIS.newdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.Floor;
import ru.FSPO.AIS.newmodels.Placement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PlacementRepository  extends CrudRepository<Placement, Long> {



     @Query(value = "SELECT DISTINCT p FROM Placement p " +
             "LEFT JOIN FETCH p.floor " +
             "LEFT JOIN FETCH p.rentedPlacement " +
             "LEFT JOIN FETCH p.serviceSet " +
             "LEFT JOIN p.requestsSet r ON r.renterLink.id = ?2 " +
             "LEFT JOIN FETCH p.requestsSet " +
             "WHERE p.floor.floorId = ?1 ")
     Set<Placement> findPlacementsByFloorId(long fId, long rId);

     default Iterable<Placement> findPlacementsByFloorIdCriteria(EntityManager entityManager, long fId, long rId){
          CriteriaBuilder cb = entityManager.getCriteriaBuilder();
          CriteriaQuery<Placement> query = cb.createQuery(Placement.class);
          Root<Placement> placementRoot = query.from(Placement.class);
          Fetch<Placement, Floor> floorFetch = placementRoot.fetch("");
          return new ArrayList<Placement>();
     };




}
