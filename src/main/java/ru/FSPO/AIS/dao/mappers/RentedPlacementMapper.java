package ru.FSPO.AIS.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.FSPO.AIS.models.RentedPlacement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RentedPlacementMapper implements RowMapper<RentedPlacement> {
    @Override
    public RentedPlacement mapRow(ResultSet resultSet, int i) throws SQLException {
        RentedPlacement rentedPlacement = new RentedPlacement();
        rentedPlacement.setRPlacementId(resultSet.getInt("r_placement_id"));
        rentedPlacement.setRent(resultSet.getInt("rent"));
        rentedPlacement.setPlacementId(resultSet.getInt("placement_id"));
        rentedPlacement.setRenterId(resultSet.getInt("rent"));
        rentedPlacement.setStartOfRent(resultSet.getDate("start_of_rent"));
        rentedPlacement.setEndOfRent(resultSet.getDate("end_of_rent"));
        return rentedPlacement;
    }
}
