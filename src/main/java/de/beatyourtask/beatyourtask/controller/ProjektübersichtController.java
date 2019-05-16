package de.beatyourtask.beatyourtask.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


@Controller
public class ProjektübersichtController {

    @GetMapping("/Projektverwaltung/Projektübersicht/")
    public String showHome() {
        return "/Projektverwaltung/Projektübersicht";
    }

}
