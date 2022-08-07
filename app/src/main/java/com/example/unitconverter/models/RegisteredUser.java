package com.example.unitconverter.models;

import java.util.List;

public class RegisteredUser {
    String id;
    String email;

    public RegisteredUser(String id, String email, String profile, String name, List<String> screenShots) {
        this.id = id;
        this.email = email;
        this.profile = profile;
        this.name = name;
        this.screenShots = screenShots;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String profile;
    String name;
    List<String> screenShots;

    public RegisteredUser() {
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getScreenShots() {
        return screenShots;
    }

    public void setScreenShots(List<String> screenShots) {
        this.screenShots = screenShots;
    }
}
