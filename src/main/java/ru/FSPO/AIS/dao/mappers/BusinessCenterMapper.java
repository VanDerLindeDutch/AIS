package ru.FSPO.AIS.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.FSPO.AIS.models.BusinessCenter;

import java.sql.ResultSet;
import java.sql.SQLException;
public class BusinessCenterMapper implements RowMapper<BusinessCenter> {
    @Override
    public BusinessCenter mapRow(ResultSet resultSet, int i) throws SQLException {
        BusinessCenter businessCenter = new BusinessCenter();
        businessCenter.setBcId(resultSet.getInt("bc_id"));
        businessCenter.setAddress(resultSet.getString("address"));
        businessCenter.setName(resultSet.getString("name"));
        businessCenter.setShortName(resultSet.getString("short_name"));
        businessCenter.setBcLinkId(resultSet.getInt("bc_link_id"));
        businessCenter.setImagePath(resultSet.getString("imagePath"));
        return businessCenter;
    }
}
