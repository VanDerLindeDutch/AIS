package ru.FSPO.AIS.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.BcLinkMapper;
import ru.FSPO.AIS.dao.mappers.RenterLinkMapper;
import ru.FSPO.AIS.models.AbstractUser;
import ru.FSPO.AIS.models.BcLink;
import ru.FSPO.AIS.models.RenterLink;

import java.util.List;
import java.util.Optional;

@Component
public class RenterLinkDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RenterLinkDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RenterLink> getAll() {
        return jdbcTemplate.query("SELECT * FROM renter_link", new RenterLinkMapper());
    }

    public Optional<RenterLink> getByLogin(String login){
        return jdbcTemplate.query("SELECT * FROM renter_link WHERE login = ?", new Object[]{login}, new RenterLinkMapper()).stream().findAny();
    }

    //(first_name, birth_date, login, password, email)
    public void save(RenterLink renterLink) {
        jdbcTemplate.update("INSERT INTO renter_link (first_name, birth_date, login, password, email)  VALUES  ( ?, ?, ?, ?, ?)", renterLink.getFirstName(), renterLink.getBirthDate(), renterLink.getLogin(), renterLink.getPassword(), renterLink.getEmail());
    }

    public RenterLink getById(int id){
        return jdbcTemplate.query("SELECT * FROM renter_link WHERE renter_id = ?", new Object[]{id}, new RenterLinkMapper()).stream().findAny().orElse(null);
    }

    public RenterLink login(RenterLink renterLink) {
        return jdbcTemplate.query("SELECT * FROM renter_link WHERE login = ? and password = ?", new Object[]{renterLink.getLogin(), renterLink.getPassword()}, new RenterLinkMapper()).stream().findAny().orElse(null);
    }

}
