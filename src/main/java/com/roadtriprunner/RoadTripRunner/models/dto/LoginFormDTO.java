package com.roadtriprunner.RoadTripRunner.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginFormDTO {


    @NotNull
    @NotBlank(message="Please enter a username for the account")
    @Size(min=5, max=25, message="Invalid username. Must be between 5 and 25 characters.")
    private String username;

    @NotBlank(message="Please enter a unique password")
    @NotNull
    @Size(min=8, max=40, message="Invalid password. Must be between 8 and 40 characters.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
