package com.segal.lqtauditproject.Models;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Hossein on 11/16/2017.
 */

public class LoginInfo extends RealmObject {
    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String username;
    private Date loggedInAt;
    private String token;
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoggedInAt() {
        return loggedInAt;
    }

    public void setLoggedInAt(Date loggedInAt) {
        this.loggedInAt = loggedInAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
