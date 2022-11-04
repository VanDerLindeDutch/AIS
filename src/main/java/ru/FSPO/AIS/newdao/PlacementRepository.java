package ru.FSPO.AIS.newdao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.Placement;
import ru.FSPO.AIS.newmodels.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface PlacementRepository extends CrudRepository<Placement, Long> {


    @Query(value = "SELECT DISTINCT p FROM Placement p " +
            "LEFT JOIN FETCH p.floor " +
            "LEFT JOIN FETCH p.rentedPlacement pr " +
            "LEFT JOIN FETCH pr.renterLink " +
            "LEFT JOIN FETCH p.serviceSet " +
            "LEFT JOIN FETCH p.requestsSet r " +
            "WHERE p.floor.floorId = ?1")
    Set<Placement> findPlacementsByFloorId(long fId);


    default List<Placement> findPlacementsByServiceSetContains(EntityManager entityManager, Set<Service> set) {
        javax.persistence.Query query = entityManager.createQuery(
                "SELECT DISTINCT p FROM Placement p " +
                        "LEFT JOIN FETCH p.floor f " +
                        "JOIN FETCH f.businessCenter b " +
                        "JOIN FETCH b.bcLink c " +
                        "LEFT JOIN FETCH p.rentedPlacement pr " +
                        "LEFT JOIN FETCH pr.renterLink " +
                        "LEFT JOIN FETCH p.serviceSet " +
                        "LEFT JOIN FETCH p.requestsSet r " +
                        "LEFT JOIN p.serviceSet ps " +
                        "WHERE ps.serviceId in (:services)");
        query.setParameter("services", set.stream().map(Service::getServiceId).collect(Collectors.toSet()));
        return query.getResultList();
    }

    default List<Placement> findPlacementsByServiceSetContainsOrdOrderByPrice(EntityManager entityManager, Set<Service> set) {
        javax.persistence.Query query = entityManager.createQuery(
                "SELECT DISTINCT p FROM Placement p " +
                        "LEFT JOIN FETCH p.floor f " +
                        "JOIN FETCH f.businessCenter b " +
                        "JOIN FETCH b.bcLink c " +
                        "LEFT JOIN FETCH p.rentedPlacement pr " +
                        "LEFT JOIN FETCH pr.renterLink " +
                        "LEFT JOIN FETCH p.serviceSet " +
                        "LEFT JOIN FETCH p.requestsSet r " +
                        "LEFT JOIN p.serviceSet ps " +
                        "WHERE ps.serviceId in (:services) " +
                        "ORDER BY p.price");
        query.setParameter("services", set.stream().map(Service::getServiceId).collect(Collectors.toSet()));
        return query.getResultList();
    }


}
