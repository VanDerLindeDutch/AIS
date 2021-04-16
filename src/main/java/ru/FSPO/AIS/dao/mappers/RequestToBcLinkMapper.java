package ru.FSPO.AIS.dao.mappers;



import org.springframework.jdbc.core.RowMapper;
import ru.FSPO.AIS.models.RequestToBcLink;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestToBcLinkMapper implements RowMapper<RequestToBcLink> {

    @Override
    public RequestToBcLink mapRow(ResultSet resultSet, int i) throws SQLException {
        RequestToBcLink request = new RequestToBcLink();
        request.setRequestId(resultSet.getInt("request_id"));
        request.setPlacementId(resultSet.getInt("placement_id"));
        request.setExpirationDate(resultSet.getDate("expiration_date"));
        request.setRenterId(resultSet.getInt("renter_id"));
        request.setStartOfRent(resultSet.getDate("start_of_rent"));
        request.setEndOfRent(resultSet.getDate("end_of_rent"));
        return request;
    }
}
