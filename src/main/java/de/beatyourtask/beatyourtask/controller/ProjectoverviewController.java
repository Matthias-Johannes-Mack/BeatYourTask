package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.ProjectService;
import de.beatyourtask.beatyourtask.services.UserService;
import de.beatyourtask.beatyourtask.validators.ProjectOverviewValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
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

    @Autowired
    ProjectOverviewValidator projectOverviewValidator;

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
    public String processDisplayAddForm(@Valid @ModelAttribute Project project, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("project",project);
            model.addAttribute("title", "Add Project");
            return "projectoverview/addProject";
        }

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
    public String processDisplayEditForm(@Valid @ModelAttribute Project project,BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("project",project);
            model.addAttribute("title", "Edit Project");
            return "projectoverview/editProject";
        }

        //Saving edited Project
        projectService.save(project);

        return "redirect:";
    }

    @GetMapping("leaveProject{projectId}")
    public String leaveProject(@RequestParam("projectId") Integer id){

        userService.getCurrentUser().removeProject(projectService.findById(id));
        userService.saveUser(userService.getCurrentUser()); // saving changes to database

        return "redirect:";
    }

    @GetMapping("users{projectId}")
    public String displayUsersForm(@RequestParam("projectId") Integer id, Model model){

        model.addAttribute("title", "Users");
        model.addAttribute("users", projectService.findById(id).getUsers());
        model.addAttribute("user", new User());
        model.addAttribute("project", id);

        return "projectoverview/users";
    }

    @PostMapping("users{projectId}")
    public String processDisplayUsersForm(@RequestParam("projectId") Integer id, @ModelAttribute User user){

        projectService.findById(id).addUser(userService.getUserByEmail(user.getEmail()));
        projectService.save(projectService.findById(id));

        return "redirect:/projectoverview/users?projectId=" + id;
    }

    @GetMapping("users/delete{projectId, userId}")
    public String processDeleteUser(@RequestParam("userId") Integer userId, @RequestParam("projectId") Integer projectId){

        projectService.findById(projectId).removeUser(userService.getUserById(userId));
        projectService.save(projectService.findById(projectId));

        return "redirect:/projectoverview/users?projectId=" + projectId;
    }

}
