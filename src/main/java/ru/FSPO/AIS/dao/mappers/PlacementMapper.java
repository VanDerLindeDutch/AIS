package ru.FSPO.AIS.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.FSPO.AIS.models.Placement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlacementMapper implements RowMapper<Placement> {

    @Override
    public Placement mapRow(ResultSet resultSet, int i) throws SQLException {
        Placement placement = new Placement();
        placement.setPlacementId(resultSet.getInt("placement_id"));
        placement.setFloorId(resultSet.getInt("floor_id"));
        placement.setPrice(resultSet.getInt("price"));
        placement.setSurface(resultSet.getInt("surface"));
        placement.setImagePath(resultSet.getString("image_path"));
        return placement;
    }
}
