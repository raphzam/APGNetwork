package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }

    @GetMapping("/signup")
    public String loadSignUpForm(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/processregister")
    public String processRegistration(@ModelAttribute User user, Model model){

        user.setEnabled(true);
        userRepository.save(user);
        model.addAttribute("confirmation", "New profile created. Check email confirmation and proceed to login");

        Role role = new Role(user.getUsername(), "ROLE_USER");
        roleRepository.save(role);

        return "login";
    }
}
