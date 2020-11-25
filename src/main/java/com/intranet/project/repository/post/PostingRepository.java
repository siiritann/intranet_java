package com.intranet.project.repository.post;


import com.intranet.project.controller.post.PostingResponseFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostingRepository {


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void createPosting(PostingEntity postingEntity) {
        String sql = "INSERT INTO posting (user_id, date, heading, body) VALUES (:userId, :date, :heading, :body)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", postingEntity.getUserId());
        paramMap.put("date", postingEntity.getDate());
        paramMap.put("heading", postingEntity.getHeading());
        paramMap.put("body", postingEntity.getBody());
        jdbcTemplate.update(sql, paramMap);


    }

    public List<PostingEntity> getListOfPostings() {
        String sql = "SELECT * FROM posting";
        Map<String, Object> paramMap = new HashMap<>();
        List<PostingEntity> postingEntityList = jdbcTemplate.query(sql, paramMap, new PostingEntityRowMapper());

        return postingEntityList;
    }
}
