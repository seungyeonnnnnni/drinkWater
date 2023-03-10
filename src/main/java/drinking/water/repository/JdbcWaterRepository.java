package drinking.water.repository;

import drinking.water.domain.Water;
import drinking.water.entity.DrinkStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Repository
public class JdbcWaterRepository implements WaterRepository {


    private final JdbcTemplate jdbcTemplate;

    public JdbcWaterRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Water save(Water water) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Water").usingGeneratedKeyColumns("waterId");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("userId", water.getUser().getUserId());
        parameters.put("capacity", water.getCupSize());
        parameters.put("goal", water.getGoal());
        parameters.put("remainCup", water.getRemainCup());
        parameters.put("status", water.getCurrent());


        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
//        water.setUserId(key.intValue());

        return water;
    }


    @Override
    public Water update(Water water) {
//        water.setUserId(water.getUserId());
//        store.put(water.getUserId(), water);
//        return water;



        // status 변경
//        jdbcTemplate.update("update Water set status=? where userId=?", water.getStatus(), water.getUserId());
        // capacity 변경 -> 쿼리 한번에 진행하는 방법 고안
//        jdbcTemplate.update("update Water set capacity=? where userId=?", water.getCapacity(), water.getUserId());


        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Drink").usingColumns("userId","waterId","ts","drinkHow"); ///????


        Map<String, Object> parameters = new HashMap<>();
        List<DrinkStatus> drinkStatus = water.getDrinkStatus();


        for (DrinkStatus d : drinkStatus) {
            parameters.put("drinkDate", d.getDrinkDate());
            parameters.put("how", d.getHow());
        }

//        for (Date date : drinkCnt.keySet()) {
//            parameters.put("userId", water.getUser().getUserId());
//            parameters.put("waterId", water.getWaterId());
//
//            parameters.put("ts", Timestamp.valueOf(LocalDateTime.now()));
//            parameters.put("drinkHow", drinkCnt.get(date).intValue());
//
//            log.info("ts={},drinkHow={}", date, drinkCnt.get(date).intValue());
//
//            jdbcInsert.execute(new MapSqlParameterSource(parameters));
//
//        } // 계속 저장되는지 확인


        return null;
//        return findById(water.getUser().getUserId());
    }

    @Override
    public Optional<Water> findById(Long userId) {

        List<Water> result = jdbcTemplate.query("select * from Water where userId = ?", waterRowMapper(), userId);
        log.info(result.toString());
        return Optional.ofNullable(result.get(0));
    }

    @Override
    public List<Map<Integer, Integer>> findByIdDrink(int userId) {
        return null;
    }

    @Override
    public List<DrinkStatus> findAllByUserId(Long userId) {
        return null;
    }

    private RowMapper<Water> waterRowMapper() {
        return (rs,rowNum) -> {
            Water water = new Water();

//            water.setUser(rs.getInt("userId"));
            water.setCupSize(rs.getInt("capacity"));
            water.setGoal(rs.getInt("goal"));
            water.setCurrent(rs.getInt("status"));
            water.setRemainCup(rs.getInt("remainCup"));



//            water.setDrinkCnt((Map<Integer, Integer>)rs.getObject(1));
            return water;
        };
    }
}
