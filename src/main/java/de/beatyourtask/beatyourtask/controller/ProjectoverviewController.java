package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.services.ProjectService;
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

    /**
     * Loads the Projectoverview with all projects in the database
     * @param model contains all saved projects and the title
     * @return Projectview.html
     */
    @GetMapping("")
    public String displayProjects(Model model) {
        model.addAttribute("title", "Projectoverview");
        model.addAttribute("listProjects", projectService.getAllProjects());
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

        //Testing
        System.out.println(project);

        projectService.saveProject(project);

        return "redirect:";
    }
}
