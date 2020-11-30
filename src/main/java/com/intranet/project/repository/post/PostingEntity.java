package com.intranet.project.repository.post;

import java.sql.Timestamp;

/**
 * REPOSITORY POST ENTITY
 */
public class PostingEntity {
    private Long id;
    private Long userId;
    private Timestamp date;
    private String heading;
    private String body;

    public PostingEntity(Long userId, Timestamp date, String heading, String body) {
        this.userId = userId;
        this.date = date;
        this.heading = heading;
        this.body = body;
    }
    public PostingEntity(Long id, Long userId, String heading, String body) {
        this.id = id;
        this.userId = userId;
        this.date = new Timestamp(System.currentTimeMillis());
        this.heading = heading;
        this.body = body;
    }
    public PostingEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
