package com.roadtriprunner.RoadTripRunner.models.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


}
