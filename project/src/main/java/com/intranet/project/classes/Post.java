package com.intranet.project.classes;

import java.sql.Timestamp;

public class Post {
    private int id;
    private int userId;
    private Timestamp date;
    private String heading;
    private String body;

    public Post(int userId, String heading, String body) {
        this.userId = userId;
        this.date = new Timestamp(System.currentTimeMillis());
        this.heading = heading;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
