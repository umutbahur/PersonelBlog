package com.example.PersonalBlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.PersonalBlog.exceptions.UserAlreadyExistsException;
import com.example.PersonalBlog.model.User;
import com.example.PersonalBlog.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String singupForm(Model model){
        model.addAttribute("user", new User());
        return "singup";
    }

    @PostMapping("/signup")
    public String signupProcess(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        user.setRole("ROLE_USER");
        user.setPassword("{noop}" + user.getPassword());
        try {
            userService.registerNewUser(user);
            redirectAttributes.addFlashAttribute("success", "Sign up successful! Please log in.");
            redirectAttributes.addAttribute("success", "Sign up successful! Please log in.");
            return "redirect:/login";
        } catch (UserAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("error", "A user with this email is already exist.");
            return "redirect:/signup";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "An error is occured during sign up. Please try again later.");
            return "redirect:/signup";
        }
    }
}
