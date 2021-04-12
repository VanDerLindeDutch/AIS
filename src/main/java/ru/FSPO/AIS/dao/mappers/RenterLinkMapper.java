package ru.FSPO.AIS.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.FSPO.AIS.models.BcLink;
import ru.FSPO.AIS.models.RenterLink;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RenterLinkMapper implements RowMapper<RenterLink> {
    @Override
    public RenterLink mapRow(ResultSet resultSet, int i) throws SQLException {
        RenterLink renterLink = new RenterLink();
        renterLink.setId(resultSet.getInt("renter_id"));
        renterLink.setLogin(resultSet.getString("login"));
        renterLink.setPassword(resultSet.getString("password"));
        renterLink.setCompanyId(resultSet.getInt("company_id"));
        renterLink.setBirthDate(resultSet.getDate("birth_date"));
        renterLink.setEmail(resultSet.getString("email"));
        renterLink.setFirstName(resultSet.getString("first_name"));
        return renterLink;
    }
}
