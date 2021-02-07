package ru.FSPO.AIS.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.BusinessCenterMapper;
import ru.FSPO.AIS.models.BusinessCenter;

import java.util.List;

@Component
public class BusinessCenterDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BusinessCenterDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(BusinessCenter businessCenter){
        jdbcTemplate.update("INSERT INTO business_center (name, short_name, address, bc_link_id, imagePath) values(?, ?, ?, ?, ?)", businessCenter.getName(), businessCenter.getShortName(), businessCenter.getAddress(), businessCenter.getBcLinkId(), businessCenter.getImagePath());
    }

    public List<BusinessCenter> getAll(){
        return jdbcTemplate.query("SELECT * FROM business_center", new BusinessCenterMapper());
    }

    public BusinessCenter get(int id){
        return jdbcTemplate.query("SELECT * FROM business_center WHERE bc_id = ?", new Object[]{id}, new BusinessCenterMapper()).stream().findAny().orElse(null);
    }

    public void update(int id, BusinessCenter businessCenter){
        jdbcTemplate.update("UPDATE business_center SET name = ?, short_name = ?, address = ?, imagePath = ? WHERE bc_id=?", businessCenter.getName(), businessCenter.getShortName(), businessCenter.getAddress(), businessCenter.getImagePath(), id);
    }

    public void drop(int id){
        jdbcTemplate.update("DELETE FROM business_center WHERE bc_id=?", new Object[]{id});
    }
}
