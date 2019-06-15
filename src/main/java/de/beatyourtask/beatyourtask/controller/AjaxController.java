package de.beatyourtask.beatyourtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AjaxController {

    @PostMapping("/ajax")
    public String showHome( @RequestParam("json") String json) {
        System.out.println("in ajax");
        System.out.println( json );

      return "Home";
    }




}
