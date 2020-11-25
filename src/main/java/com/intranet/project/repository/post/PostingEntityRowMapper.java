package com.intranet.project.repository.post;

import com.intranet.project.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostingEntityRowMapper implements RowMapper<PostingEntity> {
    @Autowired
    UserRepository userRepository;

    @Override
    public PostingEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        PostingEntity postingEntity = new PostingEntity();
        postingEntity.setId(resultSet.getLong("id"));
        postingEntity.setUserId(resultSet.getLong("user_id"));
        postingEntity.setDate(resultSet.getTimestamp("date"));
        postingEntity.setHeading(resultSet.getString("heading"));
        postingEntity.setBody(resultSet.getString("body"));

        return postingEntity;
    }
}
