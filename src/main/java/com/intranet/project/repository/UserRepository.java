package com.intranet.project.repository;

import com.intranet.project.repository.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // 1 - CREATE ACCOUNT
    public Long createUser(UserEntity userEntity){
        String sql = "INSERT INTO intranetuser (username, password, email, is_admin) VALUES (:username, :password, :email, false)";
        Map<String, Object> paramMap = new HashMap<>();
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        String email = userEntity.getEmail();
        paramMap.put("username", username);
        paramMap.put("password", password);
        paramMap.put("email", email);

        // returns id of the user that was just added
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
        return (Long) keyHolder.getKeys().get("id");
    }

    // 2 - GET USER ID BY USERNAME
    public Long getUserIdByUsername(String username){
        String sql = "SELECT id FROM intranetuser WHERE username = :username";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        Long id = jdbcTemplate.queryForObject(sql, paramMap, Long.class);
        return id;
    }
}
