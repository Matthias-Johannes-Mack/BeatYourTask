package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;


/**
 * Controller for the Project reload | wirefire | Spring 19
 */
@Controller
public class ReloadProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/Projectoverview/")
    public String showRegisterForm(Model model) {
        model.addAttribute("listProjects", projectService.getAllProjects());
        return "Projectoverview";
    }
}
