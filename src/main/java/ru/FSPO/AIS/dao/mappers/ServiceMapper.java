package ru.FSPO.AIS.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.FSPO.AIS.models.Service;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ServiceMapper implements RowMapper<Service> {


    @Override
    public Service mapRow(ResultSet resultSet, int i) throws SQLException {
        Service service = new Service();
        service.setServiceId(resultSet.getInt("service_id"));
        service.setDescription(resultSet.getString("description"));

        return service;
    }
}
