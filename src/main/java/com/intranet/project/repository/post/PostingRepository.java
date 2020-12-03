package com.intranet.project.repository.post;


import com.intranet.project.controller.post.PostingResponseFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public void updatePosting(PostingEntity postingEntity) {
        String sql = "UPDATE posting SET date = :date, heading = :heading, body = :body WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", postingEntity.getId());
        paramMap.put("date", postingEntity.getDate());
        paramMap.put("heading", postingEntity.getHeading());
        paramMap.put("body", postingEntity.getBody());
        jdbcTemplate.update(sql, paramMap);
    }

    public PostingEntity getPostingById(Long postingId) {
        String sql = "SELECT * FROM posting WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", postingId);
        return jdbcTemplate.queryForObject(sql, paramMap, new PostingEntityRowMapper());
    }

    public List<PostingEntity> getListOfPostings() {
        String sql = "SELECT * FROM posting ORDER BY date desc";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.query(sql, paramMap, new PostingEntityRowMapper());
    }

    public List<PostingEntity> getUserPostings(Long userId) {
        String sql = "SELECT * FROM posting WHERE user_id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", userId);
        return jdbcTemplate.query(sql, paramMap, new PostingEntityRowMapper());
    }

    public int deletePostingById(Long postingId){
        String sql = "DELETE FROM posting WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", postingId);
        return jdbcTemplate.update(sql, paramMap);
    }

    public void deleteUserPostings(Long userId) {
        String sql = "DELETE FROM posting WHERE user_id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", userId);
        jdbcTemplate.update(sql, paramMap);
    }


}
