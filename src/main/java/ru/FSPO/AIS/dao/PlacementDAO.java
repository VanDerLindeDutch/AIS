package ru.FSPO.AIS.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.FSPO.AIS.dao.mappers.PlacementMapper;
import ru.FSPO.AIS.dao.mappers.RentedPlacementMapper;
import ru.FSPO.AIS.dao.mappers.ServiceMapper;
import ru.FSPO.AIS.models.Placement;
import ru.FSPO.AIS.models.RentedPlacement;
import ru.FSPO.AIS.models.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import java.util.stream.IntStream;

@Component
public class PlacementDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public PlacementDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Placement> getAllforOneFloor(int floorId){
        return jdbcTemplate.query("SELECT * FROM placement WHERE floor_id = ?",new Object[]{floorId}, new PlacementMapper());
    }


    public Placement get(int id){
        return jdbcTemplate.query("SELECT * FROM placement WHERE placement_id = ?", new Object[]{id} ,new PlacementMapper()).stream().findAny().orElse(null);
    }


    public void delete(int id){
        jdbcTemplate.update("DELETE FROM placement WHERE placement_id = ?", new Object[]{id});
    }


    public List<Placement> getRequestedByLandlordID(int landlordId){
        return jdbcTemplate.query("SELECT * FROM placement p JOIN request_to_bc_link r ON r.placement_id=p.placement_id JOIN floor f ON p.floor_id=f.floor_id JOIN business_center b ON b.bc_link_id = ? AND f.bc_id = b.bc_id ORDER BY r.request_id", new Object[]{landlordId}, new PlacementMapper());
    }

    public List<Placement> getJoinPlacements(int renterID){
        return jdbcTemplate.query("SELECT * FROM placement p JOIN rented_placement r ON r.placement_id = p.placement_id AND r.renter_id = ?", new Object[]{renterID}, new PlacementMapper());
    }


    public Map<Placement, List<Service>> getJoinServices(Integer... servicesId){
        String SQL = "SELECT * FROM placement p JOIN placement_service ps ON p.placement_id=ps.placement_id AND ps.service_id = ?";
        String SQLj = "SELECT * FROM service s JOIN placement_service ps ON s.service_id=ps.service_id AND ps.placement_id = ?";
        List<List<Placement>> list = new ArrayList<>();
        Arrays.stream(servicesId).forEach(x->list.add(jdbcTemplate.query(SQL, new Object[]{x}, new PlacementMapper())));
        IntStream.range(1,list.size())
                .forEach(x->list.get(0)
                        .retainAll(list.get(x)));
        Map<Placement, List<Service>> mapList = new HashMap<>();
        list.get(0)
                .forEach(x->mapList
                        .put(x, jdbcTemplate.query(SQLj
                                , new Object[]{x.getPlacementId()}
                                , new ServiceMapper())));
        return mapList;
    }


    public int insert(Placement placement){
        String SQL ="INSERT INTO placement (surface, price, floor_id, image_path) values( ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, (int) placement.getSurface());
            preparedStatement.setInt(2, (int) placement.getPrice());
            preparedStatement.setInt(3, (int) placement.getFloorId());
            preparedStatement.setString(4,placement.getImagePath());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

}
