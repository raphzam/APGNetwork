package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PostRepository postRepository;

    @RequestMapping("/")
    public String index(Model model, Principal principal) {

        if (principal!=null) {
            model.addAttribute("sessionUser", principal.getName());
        }

        model.addAttribute("newPost", new Post());
        model.addAttribute("feed", postRepository.findAll());
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/secure")
    public String secure() {
        return "secure";
    }

    @GetMapping("/signup")
    public String loadSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/processregister")
    public String processRegistration(@ModelAttribute User user, Model model) {

        user.setEnabled(true);
        userRepository.save(user);
        model.addAttribute("confirmation", "New profile created. Check email confirmation and proceed to login");

        Role role = new Role(user.getUsername(), "ROLE_USER");
        roleRepository.save(role);

        return "login";
    }

    @PostMapping("/processpost")
    public String processPost(@ModelAttribute Post post, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            post.setAuthor(userRepository.findByUsername(username));
        }
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/newpost")
    public String loadPostForm() {
        return "";
    }

    @RequestMapping("/updatepost/{id}")
    public String updatePost(@PathVariable("id") long id, Principal principal, Model model) {

        Post thisPost = postRepository.findById(id).get();

        String username = principal.getName();
        User sessionUser = userRepository.findByUsername(username);

        if (sessionUser == thisPost.getAuthor()) {
            model.addAttribute("post", thisPost);
            return "postform";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/deletepost/{id}")
    public String deletePost(@PathVariable("id") long id, Principal principal, Model model){

        Post thisPost = postRepository.findById(id).get();

        String username = principal.getName();
        User sessionUser = userRepository.findByUsername(username);

        if (sessionUser == thisPost.getAuthor()){
            postRepository.delete(thisPost);
        }

        return "redirect:/";

    }


}
