package com.roadtriprunner.RoadTripRunner.models;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
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
    @NotBlank(message="Please enter a username")
    @Size(min=5, max=25, message="Invalid username. Must be between 5 and 25 characters.")
    private String username;

    private String pwHash;


    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public User(String username, String password) {
        this.username = username;
        this.pwHash = password;
    }

    public User() {
    }


    public boolean isPasswordMatching(String password) {
        return encoder.matches(password, pwHash);
    }

}
