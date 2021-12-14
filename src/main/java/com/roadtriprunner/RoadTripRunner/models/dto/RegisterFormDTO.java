package com.roadtriprunner.RoadTripRunner.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegisterFormDTO extends LoginFormDTO {

    private String verifyPassword;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    @Email(message="Please enter a properly formatted email")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}