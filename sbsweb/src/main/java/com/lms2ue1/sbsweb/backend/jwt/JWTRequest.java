package com.lms2ue1.sbsweb.backend.jwt;

import java.io.Serializable;

/**
 * Used in jwt requests for easy access to important variables
 * @author Luca Anthony Schwarz (sunfl0w)
 */
public class JWTRequest implements Serializable {
    private String username;
    private String password;

    public JWTRequest() {
    }

    public JWTRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}