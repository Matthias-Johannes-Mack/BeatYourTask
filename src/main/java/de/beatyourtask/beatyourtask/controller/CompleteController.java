package de.beatyourtask.beatyourtask.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



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
}


