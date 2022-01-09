package com.roadtriprunner.RoadTripRunner.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class LoginFormDTO {

    @NotNull
    @NotBlank(message="Please enter a username for the account")
    @Size(min=5, max=25, message="Invalid username. Must be between 5 and 25 characters.")
    private String username;

    @NotBlank(message="Please enter a unique password")
    @NotNull
    private String password;


}
