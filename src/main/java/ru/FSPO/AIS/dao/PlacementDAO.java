package ru.FSPO.AIS.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.PlacementMapper;
import ru.FSPO.AIS.models.Placement;
import ru.FSPO.AIS.models.RentedPlacement;

import java.util.List;

@Component
public class PlacementDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public PlacementDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Placement> getAllforOneFloor(int floorId){
        return jdbcTemplate.query("SELECT * FROM placement WHERE floor_id = ?",new Object[]{floorId}, new PlacementMapper());
    }


    public Placement get(int id){
        return jdbcTemplate.query("SELECT * FROM placement WHERE placement_id = ?", new Object[]{id} ,new PlacementMapper()).stream().findAny().orElse(null);
    }


    public void delete(int id){
        jdbcTemplate.update("DELETE FROM placement WHERE placement_id = ?", new Object[]{id});
    }


    public List<Placement> getRequestedByLandlordID(int landlordId){
        return jdbcTemplate.query("SELECT * FROM placement p JOIN request_to_bc_link r ON r.placement_id=p.placement_id JOIN floor f ON p.floor_id=f.floor_id JOIN business_center b ON b.bc_link_id = ? AND f.bc_id = b.bc_id ORDER BY r.request_id", new Object[]{landlordId}, new PlacementMapper());
    }


    public void insert(Placement placement){
        jdbcTemplate.update("INSERT INTO placement (surface, price, floor_id, image_path) values( ?, ?, ?, ?)", placement.getSurface(), placement.getPrice(), placement.getFloorId(), placement.getImagePath());
    }

}
