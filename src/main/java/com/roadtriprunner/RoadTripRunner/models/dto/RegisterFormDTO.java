package com.roadtriprunner.RoadTripRunner.models.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterFormDTO extends LoginFormDTO {

    @NotNull
    @NotBlank(message="Please verify your password.")
    private String verifyPassword;

    @NotBlank(message="Please enter your name.")
    @NotNull
    private String name;

    @NotBlank(message="Please provide an email for the account.")
    @NotNull
    @Email(message="Please enter a properly formatted email.")
    private String email;

}
