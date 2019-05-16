package de.beatyourtask.beatyourtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
public class HomeController {

 @GetMapping("/")
 public String showHome() {
     return "home";
 }
}
