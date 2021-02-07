package ru.FSPO.AIS.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.BcLinkMapper;
import ru.FSPO.AIS.dao.mappers.RenterLinkMapper;
import ru.FSPO.AIS.models.BcLink;
import ru.FSPO.AIS.models.RenterLink;

import java.util.List;

@Component
public class RenterLinkDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RenterLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RenterLink> getAll() {
        return jdbcTemplate.query("SELECT * FROM renter_link", new RenterLinkMapper());
    }

    //(first_name, birth_date, login, password, email)
    public void save(RenterLink renterLink) {
        jdbcTemplate.update("INSERT INTO renter_link (first_name, birth_date, login, password, email)  VALUES  ( ?, ?, ?, ?, ?)", renterLink.getFirstName(), renterLink.getBirthDate(), renterLink.getLogin(), renterLink.getPassword(), renterLink.getEmail());
    }

    public RenterLink login(RenterLink renterLink) {
        return jdbcTemplate.query("SELECT * FROM renter_link WHERE login = ? and password = ?", new Object[]{renterLink.getLogin(), renterLink.getPassword()}, new RenterLinkMapper()).stream().findAny().orElse(null);
    }

}
