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
        jdbcTemplate.update("INSERT INTO rented_placement (rent, start_of_rent, end_of_rent, placement_id, renter_id) VALUES (?, ?, ?, ?, ?)", rentedPlacement.getRent(), rentedPlacement.getStartOfRent(), rentedPlacement.getEndOfRent(), rentedPlacement.getPlacementId(), rentedPlacement.getRenterId() );
    }

    public List<RentedPlacement> getAllForUser(int renterID){
        return jdbcTemplate.query("SELECT * FROM rented_placement WHERE renter_id = ?", new Object[]{renterID}, new RentedPlacementMapper());
    }

}
