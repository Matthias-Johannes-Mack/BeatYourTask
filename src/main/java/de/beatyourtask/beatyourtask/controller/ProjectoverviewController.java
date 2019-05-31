package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.services.ProjectService;
import de.beatyourtask.beatyourtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;


/**
 * Controller handlling all requests for
 * /projectoverview
 */
@Controller
@RequestMapping(value ="projectoverview")
public class ProjectoverviewController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    /**
     * Loads the Projectoverview with all projects in the database
     * @param model contains all saved projects and the title
     * @return Projectview.html
     */
    @GetMapping("")
    public String displayProjects(Model model) {
        model.addAttribute("title", "Projectoverview");
        model.addAttribute("listProjects", userService.getCurrentUser().getProjects());

        return "projectoverview/projects";
    }

    @GetMapping("add")
    public String displayAddForm(Model model){
        model.addAttribute("title", "Add Project");
        model.addAttribute("project", new Project()); // projects thats being filled in the form

        return "projectoverview/addProject";
    }

    @PostMapping("add")
    public String processDisplayAddForm(@ModelAttribute Project project, Model model){
        model.addAttribute("title", "Projectoverview");

        //Saving to current user
        project.addUser(userService.getCurrentUser());
        projectService.saveProject(project);

        return "redirect:";
    }
}
