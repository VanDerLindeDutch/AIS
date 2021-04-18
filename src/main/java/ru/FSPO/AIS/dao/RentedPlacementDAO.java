package ru.FSPO.AIS.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.RentedPlacementMapper;
import ru.FSPO.AIS.models.RentedPlacement;

import java.util.List;

@Component
public class RentedPlacementDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RentedPlacementDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(RentedPlacement rentedPlacement){
        jdbcTemplate.update("INSERT INTO rented_placement (total_amount, start_of_rent, end_of_rent, placement_id, renter_id) VALUES (?, ?, ?, ?, ?)", rentedPlacement.getTotalAmount(), rentedPlacement.getStartOfRent(), rentedPlacement.getEndOfRent(), rentedPlacement.getPlacementId(), rentedPlacement.getRenterId() );
    }

    public List<RentedPlacement> getAllForUser(int renterID){
        return jdbcTemplate.query("SELECT * FROM rented_placement WHERE renter_id = ?", new Object[]{renterID}, new RentedPlacementMapper());
    }

    public List<RentedPlacement> getForFloor(int fID){
        return jdbcTemplate.query("SELECT * FROM rented_placement JOIN placement ON rented_placement.placement_id = placement.placement_id AND placement.floor_id = ?", new Object[]{fID}, new RentedPlacementMapper());
    }

}
