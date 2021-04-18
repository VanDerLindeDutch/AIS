package ru.FSPO.AIS.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.FloorMapper;
import ru.FSPO.AIS.models.Floor;
import ru.FSPO.AIS.services.FloorException;

import java.util.List;

@Component
public class FloorDAO {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FloorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Floor> getBcsFloors(int bcID) {
        return jdbcTemplate.query("SELECT * FROM floor WHERE bc_id = ?",new Object[]{bcID}, new FloorMapper());
    }

    public void delete(int floorId) throws Exception{
        jdbcTemplate.update("DELETE FROM floor WHERE floor_id = ?", new Object[]{floorId});
    }

    public void insert(Floor floor) {
//        if(getBcsFloors((int) floor.getBcId()).stream().noneMatch(floor1 -> floor1.getFloorNumber()==floor.getFloorNumber())){
//            throw new FloorException("Повторяющийся номер этажа");
//        }
        jdbcTemplate.update("INSERT INTO floor (floor_number, bc_id, description, image_path) VALUES(?, ?, ?, ?)", floor.getFloorNumber(), floor.getBcId(), floor.getDescription(), floor.getImagePath());
    }

    public Floor get(int id) {
        return jdbcTemplate.query("SELECT * FROM floor WHERE floor_id = ?", new Object[]{id}, new FloorMapper()).stream().findAny().orElse(null);
    }
}
