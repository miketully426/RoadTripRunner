package com.roadtriprunner.RoadTripRunner.controllers;

import com.roadtriprunner.RoadTripRunner.data.UserRepository;
import com.roadtriprunner.RoadTripRunner.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")

public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

//    @GetMapping()
//    public String renderRegistrationForm(Model model){
//        model.addAttribute("title", "Register");
//        return "register";
//    };
//
//
//
//
//    @PostMapping()
//    public String processRegistrationForm(@ModelAttribute @Valid User newUser, Errors errors, Model model){
//        if (errors.hasErrors()){
//            model.addAttribute("title","Register");
//            return "redirect:";
//        }
//
//        User existingUser = userRepository.findByUsername(newUser.getUsername());
//
//
//        //see if username is already in database (cannot make a duplicate)
//        if (existingUser != null) {
//            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists. Please choose" +
//                    "a different password");
//            model.addAttribute("title", "Register");
//            return "register";
//        }
//
//        //check to see if passwords match
//        /*
//         */
//
//
//        //save user
//
//
//        userRepository.save(newUser);
//        return "register";
//    }





//    @PostMapping("/register")
//    public String processRegistrationForm()
}