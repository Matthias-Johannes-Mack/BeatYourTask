package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.ProjectService;
import de.beatyourtask.beatyourtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;


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
        model.addAttribute("projects", userService.getCurrentUser().getProjects());

        return "projectoverview/projects";
    }

    @GetMapping("add")
    public String displayAddForm(Model model){
        model.addAttribute("title", "Add Project");
        model.addAttribute("project", new Project()); // projects thats being filled in the form

        return "projectoverview/addProject";
    }

    @PostMapping("add")
    public String processDisplayAddForm(@ModelAttribute Project project){
        //Saving to current user
        project.addUser(userService.getCurrentUser());
        projectService.save(project);

        return "redirect:";
    }

    @GetMapping("editProject{projectId}")
    public String displayEditForm(@RequestParam("projectId") Integer id,Model model){

        model.addAttribute("title", "Edit Project");
        model.addAttribute("project", projectService.findById(id));

        return "projectoverview/editProject";
    }

    @PostMapping("editProject")
    public String processDisplayEditForm(@ModelAttribute Project project){

        //Saving edited Project
        projectService.save(project);

        return "redirect:";
    }

    @GetMapping("leaveProject{projectId}")
    public String leaveProject(@RequestParam("projectId") Integer id){

        System.out.println("in leave");
        userService.getCurrentUser().removeProject(projectService.findById(id));
        userService.saveUser(userService.getCurrentUser()); // saving changes to database

        return "redirect:";
    }

    @GetMapping("users{projectId}")
    public String displayUsersForm(@RequestParam("projectId") Integer id, Model model){

        for (User user:projectService.findById(id).getUsers()) {
            System.out.println(user.getLastname());
            System.out.println(user.getSurname());
        }
        model.addAttribute("title", "Users");
        model.addAttribute("users", projectService.findById(id).getUsers());

        return "projectoverview/users";
    }



}
