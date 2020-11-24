package com.intranet.project.repository.post;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * REPOSITORY POST ENTITY
 */
public class PostEntity {
    private BigInteger id;
    private BigInteger userId;
    private Timestamp date;
    private String heading;
    private String body;

    public PostEntity(BigInteger id, BigInteger userId, Timestamp date, String heading, String body) {
        this.id = id;
        this.userId = userId;
        //this.date = new Timestamp(System.currentTimeMillis());
        this.date = date;
        this.heading = heading;
        this.body = body;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
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