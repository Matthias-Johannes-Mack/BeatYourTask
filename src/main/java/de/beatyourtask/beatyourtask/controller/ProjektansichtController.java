package de.beatyourtask.beatyourtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjektansichtController {
    @GetMapping("/Project/")
    public String showProjekt() {
        return "Projektansicht";
    }
}
