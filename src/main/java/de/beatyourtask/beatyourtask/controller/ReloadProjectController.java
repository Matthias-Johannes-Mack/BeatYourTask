package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the Project reload | wirefire | Spring 19
 */
public class ReloadProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping("/")
    public Model showRegisterForm(Project project,
                                  Model model) {
        model.addAttribute("listProjects", projectService.getAllProjects());
        return model;
    }
}
