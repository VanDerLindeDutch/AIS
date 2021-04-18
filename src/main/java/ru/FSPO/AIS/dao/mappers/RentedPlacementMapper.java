package ru.FSPO.AIS.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.FSPO.AIS.models.RentedPlacement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RentedPlacementMapper implements RowMapper<RentedPlacement> {
    @Override
    public RentedPlacement mapRow(ResultSet resultSet, int i) throws SQLException {
        RentedPlacement rentedPlacement = new RentedPlacement();
        rentedPlacement.setRPlacementId(resultSet.getInt("r_placement_id"));
        rentedPlacement.setTotalAmount(resultSet.getInt("total_amount"));
        rentedPlacement.setPlacementId(resultSet.getInt("placement_id"));
        rentedPlacement.setRenterId(resultSet.getInt("renter_id"));
        rentedPlacement.setStartOfRent(resultSet.getDate("start_of_rent"));
        rentedPlacement.setEndOfRent(resultSet.getDate("end_of_rent"));
        return rentedPlacement;
    }
}
