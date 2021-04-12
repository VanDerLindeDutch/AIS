package ru.FSPO.AIS.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.BcLinkMapper;
import ru.FSPO.AIS.dao.mappers.RenterLinkMapper;
import ru.FSPO.AIS.models.BcLink;
import ru.FSPO.AIS.models.RenterLink;

import java.util.List;
import java.util.Optional;

@Component
public class BcLinkDAO {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public BcLinkDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BcLink> getAll() {
        return jdbcTemplate.query("SELECT * FROM bc_link", new BcLinkMapper());
    }

    //(first_name, birth_date, login, password, email)
    public void insert(BcLink bcLink) {

        jdbcTemplate.update("INSERT INTO bc_link (first_name, birth_date, login, password, email)  VALUES  ( ?, ?, ?, ?, ?)", bcLink.getFirstName(), bcLink.getBirthDate(), bcLink.getLogin(), bcLink.getPassword(), bcLink.getEmail());
    }

    public Optional<BcLink> getByLogin(String login){
        return jdbcTemplate.query("SELECT * FROM bc_link WHERE login = ?", new Object[]{login}, new BcLinkMapper()).stream().findAny();
    }

    public BcLink login(BcLink bcLink) {
        return jdbcTemplate.query("SELECT * FROM bc_link WHERE login = ? and password = ?", new Object[]{bcLink.getLogin(), bcLink.getPassword()}, new BcLinkMapper()).stream().findAny().orElse(null);
    }

    public BcLink get(int id) {
        return jdbcTemplate.query("SELECT * FROM bc_link WHERE bc_link_id = ?", new Object[]{id}, new BcLinkMapper()).stream().findAny().orElse(null);
    }

}
