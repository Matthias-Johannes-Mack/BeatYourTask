package de.beatyourtask.beatyourtask.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class CompleteController {

    // added a logger
    private static final Logger logger = LoggerFactory.getLogger(CompleteController.class);

    @GetMapping("/")
    public String showHome() {
        return "Home";
    }




}


