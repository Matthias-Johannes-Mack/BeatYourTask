package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller handlling all requests for
 * /projectoverview
 */
@Controller
@RequestMapping(value ="projectoverview")
public class ProjectoverviewController {

    @Autowired
    ProjectService projectService;


    /**
     * Loads the Projectoverview with all projects in the database
     * @param model contains all saved projects and the title
     * @return Projevtview.html
     */
    @GetMapping("/")
    public String displayProjects(Model model) {
        model.addAttribute("listProjects", projectService.getAllProjects());
        model.addAttribute("title", "Projectoverview");
        return "Projectoverview";
    }
}
