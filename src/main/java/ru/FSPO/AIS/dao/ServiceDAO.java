package ru.FSPO.AIS.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.ServiceMapper;
import ru.FSPO.AIS.models.Service;

import java.util.List;

@Component
public class ServiceDAO {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public ServiceDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Service> getAll(){
        return jdbcTemplate.query("SELECT * FROM service", new ServiceMapper());
    }

    public List getAllForFloor(int flId) {
        return jdbcTemplate.queryForList("SELECT * FROM service s JOIN placement_service ps ON s.service_id = ps.service_id JOIN placement pl ON ps.placement_id=pl.placement_id AND pl.floor_id = ?",new Object[]{flId});
    }

    public void insertServiceToPlacement(int pId, int sId) {
        jdbcTemplate.update("INSERT INTO placement_service(placement_id, service_id) VALUES (?,?)", pId, sId);
    }
}

