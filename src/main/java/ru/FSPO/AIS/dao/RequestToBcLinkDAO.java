package ru.FSPO.AIS.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.RentedPlacementMapper;
import ru.FSPO.AIS.dao.mappers.RequestToBcLinkMapper;
import ru.FSPO.AIS.models.RequestToBcLink;

import java.util.List;

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
        jdbcTemplate.update("INSERT INTO request_to_bc_link(placement_id, expiration_date, renter_id, start_of_rent, end_of_rent) VALUES (?,?,?,?,?)", request.getPlacementId(), request.getExpirationDate(), request.getRenterId(), request.getStartOfRent(), request.getEndOfRent());
    }

    public RequestToBcLink getById(int id){
        return jdbcTemplate.query("SELECT * FROM request_to_bc_link WHERE request_id = ?", new Object[]{id}, new RequestToBcLinkMapper()).stream().findAny().orElse(null);
    }

}
