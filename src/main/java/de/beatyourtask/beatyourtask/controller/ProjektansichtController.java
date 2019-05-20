package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.services.TasklistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ProjektansichtController {

    @Autowired
    private TasklistService tasklistService;


    @GetMapping("/Project/")
    public String loadLists(Model model) {
        model.addAttribute("tasklists", tasklistService.getAllTaskLists());
        return "Projektansicht";
    }
}
