package com.intranet.project.repository.user;

import com.intranet.project.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Long createUser(UserEntity userEntity){
        String sql = "INSERT INTO intranetuser (username, password, email, is_admin) VALUES (:username, :password, :email, false)";
        Map<String, Object> paramMap = new HashMap<>();
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        String email = userEntity.getEmail();
        paramMap.put("username", username);
        paramMap.put("password", password);
        paramMap.put("email", email);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
        return (Long) keyHolder.getKeys().get("id");
    }

    public Long getUserIdByUsername(String username){
        String sql = "SELECT id FROM intranetuser WHERE username = :username";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        Long id = jdbcTemplate.queryForObject(sql, paramMap, Long.class);
        return id;
    }

    public UserEntity getUserById(Long id){
        String sql = "SELECT * FROM intranetuser WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        if (jdbcTemplate.query(sql, paramMap, new UserRowMapper()).size() > 0){
            return jdbcTemplate.queryForObject(sql, paramMap, new UserRowMapper());
        } else {
            throw new NotFoundException("User not found");
        }

    }

    public String getUsernameById(Long id){
        String sql = "SELECT username FROM intranetuser WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        String username = jdbcTemplate.queryForObject(sql, paramMap, String.class);
        return username;
    }

    public List getListOfUsers(){
        String sql = "SELECT * FROM intranetuser";
        Map<String, Object> paramMap = new HashMap<>();
        List <UserEntity>  usersList = jdbcTemplate.query(sql, paramMap, new UserRowMapper());
        return usersList;
    }

    public UserEntity viewUser(Long id) {
        String sql = "SELECT * FROM intranetuser WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        List<UserEntity> list = jdbcTemplate.query(sql, paramMap, new UserRowMapper());
        UserEntity userEntity = list.get(0);
        return userEntity;
    }

    public int updateUserPassword(Long id, String password){
        String sql = "UPDATE intranetuser SET password = :password WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("password", password);
        return jdbcTemplate.update(sql, paramMap);
    }

    public int deleteUserById(Long id){
        String sql = "DELETE FROM intranetuser WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.update(sql, paramMap);
    }
}
