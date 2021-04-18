package ru.FSPO.AIS.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.RentedPlacementMapper;
import ru.FSPO.AIS.dao.mappers.RequestToBcLinkMapper;
import ru.FSPO.AIS.models.RentedPlacement;
import ru.FSPO.AIS.models.RequestToBcLink;

import java.util.List;
import java.util.Map;

@Component
public class RequestToBcLinkDAO {

    private final JdbcTemplate jdbcTemplate;

    public RequestToBcLinkDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RequestToBcLink> getAll(){
        return jdbcTemplate.query("SELECT * FROM request_to_bc_link", new RequestToBcLinkMapper());
    }

    public void insert(RequestToBcLink request){
        jdbcTemplate.update("INSERT INTO request_to_bc_link(placement_id, expiration_date, renter_id, start_of_rent, end_of_rent, is_checked) VALUES (?,?,?,?,?,?)", request.getPlacementId(), request.getExpirationDate(), request.getRenterId(), request.getStartOfRent(), request.getEndOfRent(), request.isCheked());
    }

    public RequestToBcLink getById(int id){
        return jdbcTemplate.query("SELECT * FROM request_to_bc_link WHERE request_id = ?", new Object[]{id}, new RequestToBcLinkMapper()).stream().findAny().orElse(null);
    }

    public void update(RequestToBcLink request){
        jdbcTemplate.update("UPDATE request_to_bc_link SET placement_id = ?, expiration_date = ?, renter_id = ?, start_of_rent = ?, end_of_rent = ?, is_checked = ? WHERE request_id = ?", request.getPlacementId(), request.getExpirationDate(), request.getRenterId(), request.getStartOfRent(), request.getEndOfRent(), request.isCheked(), request.getRequestId());
    }

    public List<RequestToBcLink> getByRenterId(int id){
        return jdbcTemplate.query("SELECT * FROM request_to_bc_link WHERE renter_id = ?", new Object[]{id}, new RequestToBcLinkMapper());
    }


    public void delete(int id){
        jdbcTemplate.update("DELETE FROM request_to_bc_link WHERE request_id = ?", new Object[]{id});

    }

    public List<RequestToBcLink> getByBcLink(int id){
        return jdbcTemplate.query("SELECT * FROM request_to_bc_link r " +
                "JOIN placement p ON r.placement_id=p.placement_id " +
                "JOIN floor f ON p.floor_id=f.floor_id " +
                "JOIN business_center b ON b.bc_link_id = ? " +
                "AND f.bc_id = b.bc_id ORDER BY r.expiration_date",
                new Object[]{id},
                new RequestToBcLinkMapper());
    }

    public List<RequestToBcLink> getRequestsJoinPlacements(int renterId, int floorID){
        return jdbcTemplate.query("SELECT * FROM request_to_bc_link r" +
                " JOIN placement p ON p.placement_id = r.placement_id" +
                " AND p.floor_id = ? AND r.renter_id = ?",
                new Object[]{floorID,renterId},
                new RequestToBcLinkMapper());

    }

}
