package ru.FSPO.AIS.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.FSPO.AIS.models.Floor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FloorMapper implements RowMapper<Floor> {

    @Override
    public Floor mapRow(ResultSet resultSet, int i) throws SQLException {
        Floor floor = new Floor();
        floor.setFloorId(resultSet.getInt("floor_id"));
        floor.setBcId(resultSet.getInt("bc_id"));
        floor.setFloorNumber(resultSet.getInt("floor_number"));
        floor.setDescription(resultSet.getString("description"));
        floor.setImagePath(resultSet.getString("image_path"));
        return floor;
    }
}
