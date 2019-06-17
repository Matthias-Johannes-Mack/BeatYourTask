package de.beatyourtask.beatyourtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "redirect:projectoverview";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
