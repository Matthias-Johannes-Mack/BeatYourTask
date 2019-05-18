package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class CompleteController {

    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    @GetMapping("/Projektuebersicht/")
    public String showLogin() {
        return "Projektuebersicht";
    }

    @GetMapping("/Register/")
    public String showRegister() {
        return "Register";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("Users", "User", new User());
    }

    // get the mapping
    @GetMapping("/regist")
    public String registSubmit(Model model) {
        model.addAttribute("User", new User());
        return "/";
    }

    @PostMapping("/regist")
    public String showPage(@ModelAttribute("User") User user) {

        System.out.println("Date planted: " + user.getEmail()); //in reality, you'd use a logger instead :)
        return "/";
    }
}


