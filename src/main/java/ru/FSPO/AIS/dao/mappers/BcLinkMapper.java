package ru.FSPO.AIS.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.FSPO.AIS.models.BcLink;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BcLinkMapper implements RowMapper<BcLink> {
    @Override
    public BcLink mapRow(ResultSet resultSet, int i) throws SQLException {
        BcLink bcLink = new BcLink();
        bcLink.setBcLinkId(resultSet.getInt("bc_link_id"));
        bcLink.setLogin(resultSet.getString("login"));
        bcLink.setPassword(resultSet.getString("password"));
        bcLink.setBirthDate(resultSet.getDate("birth_date"));
        bcLink.setEmail(resultSet.getString("email"));
        bcLink.setFirstName(resultSet.getString("first_name"));
        return bcLink;
    }
}
