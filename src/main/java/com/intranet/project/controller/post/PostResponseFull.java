package com.intranet.project.controller.post;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * CONTROLLER FULL POST RESPONSE
 */
public class PostResponseFull {
    private BigInteger id;
    private String username;
    private Timestamp date;
    private String heading;
    private String body;

    public PostResponseFull(BigInteger id, String username, Timestamp date, String heading, String body) {
        this.id = id;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

