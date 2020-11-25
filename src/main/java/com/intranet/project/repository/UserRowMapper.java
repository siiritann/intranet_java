package com.intranet.project.repository;

import com.intranet.project.repository.user.UserEntity;
import org.apache.catalina.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserEntity> {

    @Override
    public UserEntity mapRow(ResultSet resultSet, int i) throws SQLException{
        UserEntity userEntity = new UserEntity();
        userEntity.setId(resultSet.getLong("id"));
        userEntity.setUsername(resultSet.getString("username"));
        userEntity.setIsAdmin(resultSet.getBoolean("is_admin"));
        userEntity.setEmail(resultSet.getString("email"));
        userEntity.setFirstName(resultSet.getString("first_name"));
        userEntity.setLastName(resultSet.getString("last_name"));
        userEntity.setBirthDate(resultSet.getDate("birth_date"));
        userEntity.setPhone(resultSet.getString("phone"));
        return userEntity;
    }
}
