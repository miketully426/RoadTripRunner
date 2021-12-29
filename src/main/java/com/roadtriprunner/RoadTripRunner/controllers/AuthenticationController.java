package com.roadtriprunner.RoadTripRunner.controllers;

import com.roadtriprunner.RoadTripRunner.data.UserRepository;
import com.roadtriprunner.RoadTripRunner.models.User;
import com.roadtriprunner.RoadTripRunner.models.dto.LoginFormDTO;
import com.roadtriprunner.RoadTripRunner.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;
import java.util.Optional;

@Controller
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }


    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register");
        return "register";
    }


    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "register";
        }

        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists",
                    "A user with that username already exists");
            model.addAttribute("title", "Register");
            return "register";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch",
                    "Passwords do not match");
            model.addAttribute("title", "Register");
            return "register";
        }

        User theUser = new User(registerFormDTO.getName(), registerFormDTO.getEmail(), registerFormDTO.getUsername(), registerFormDTO.getPassword());
        userRepository.save(theUser);

        return "redirect:";

    }

    @GetMapping("/login")
    public String renderLoginForm(Model model, User theUser) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Login");
        return "login";

}


    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model, User theUser) throws IOException, ScriptException, NoSuchMethodException {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }

        User user = userRepository.findByUsername(loginFormDTO.getUsername());

        if (user == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("title", "Log In");
            return "login";
        }

        String password = loginFormDTO.getPassword();

        if (!user.isPasswordMatching(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "login";
        }

        model.addAttribute("logout", "Logout");
        setUserInSession(request.getSession(), theUser);
        theUser.setUserInSession(true);
        model.addAttribute("isUserInSession", theUser.getUserInSession());

        return "trips/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model, User theUser){
        request.getSession().invalidate();
        theUser.setUserInSession(false);
        model.addAttribute("isUserInSession", theUser.getUserInSession());
        return "redirect:/login";
    }

}