package com.roadtriprunner.RoadTripRunner.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class User extends AbstractEntity {


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    @Email
    private String email;


    @NotNull
    @NotBlank
    private String username;

    @NotBlank
    @NotNull
    private String pwHash;


    public String getUsername() {
        return username;
    }


    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public User() {
    }


    public boolean isPasswordMatching(String password) {
        return encoder.matches(password, pwHash);
    }


}
