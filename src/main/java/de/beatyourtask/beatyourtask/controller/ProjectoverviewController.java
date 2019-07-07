package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.MonsterService;
import de.beatyourtask.beatyourtask.services.ProjectService;
import de.beatyourtask.beatyourtask.services.UserService;
import de.beatyourtask.beatyourtask.validators.AddUserDTO;
import de.beatyourtask.beatyourtask.validators.ProjectAddUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Controller handlling all requests for /projectoverview
 */
@Controller
@RequestMapping(value = "projectoverview")
public class ProjectoverviewController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    MonsterService monsterService;

    @Autowired
    ProjectAddUserValidator projectAddUserValidator;

    /**
     * Loads the Projectoverview with all projects in the database
     *
     * @param model contains all saved projects and the title
     * @return projects.html
     */
    @GetMapping("")
    public String displayProjects(Model model) {


        model.addAttribute("title", "Projectoverview");
        model.addAttribute("projects", userService.getCurrentUser().getProjects());

        // Stuff for the gamification like level, exp and the monster information
        model.addAttribute("Lvl", userService.getCurrentUser().getLvl());
        model.addAttribute("Exp", userService.getCurrentUser().getExp());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        //-------------------------------------------------------------------------------------------------


        return "projectoverview/projects";
    }

    /**
     * Shows form for adding a new project
     *
     * @param model contains title and project to be filled with values
     * @return addProject.html
     */
    @GetMapping("add")
    public String displayAddForm(Model model) {
        model.addAttribute("title", "Add Project");
        model.addAttribute("project", new Project());
        // Stuff for the gamification like level, exp and the monster information
        model.addAttribute("Lvl", userService.getCurrentUser().getLvl());
        model.addAttribute("Exp", userService.getCurrentUser().getExp());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        //-------------------------------------------------------------------------------------------------

        return "projectoverview/addProject";
    }

    /**
     * Processes the form for adding a project
     *
     * @param project the project created with the form by the user
     * @param result  contains (if present) errors of project validation
     * @param model   contains the created project
     * @return redirect to projectoverview
     */
    @PostMapping("add")
    public String processDisplayAddForm(@Valid @ModelAttribute Project project, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("title", "Add Project");
            // Stuff for the gamification like level, exp and the monster information
            model.addAttribute("Lvl", userService.getCurrentUser().getLvl());
            model.addAttribute("Exp", userService.getCurrentUser().getExp());
            model.addAttribute("Surname", userService.getCurrentUser().getSurname());
            model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
            model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
            model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
            model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
            model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
            //-------------------------------------------------------------------------------------------------
            return "projectoverview/addProject";
        }

        // saving created project
        project.addUser(userService.getCurrentUser());
        projectService.save(project);

        return "redirect:/projectoverview";
    }

    /**
     * Shows form for editing an existing project
     *
     * @param id    id of edited project
     * @param model model containing title and edited project
     * @return editProject.html
     */
    @GetMapping("editProject{projectId}")
    public String displayEditForm(@RequestParam("projectId") Integer id, Model model) {

        model.addAttribute("title", "Edit Project");
        model.addAttribute("project", projectService.findById(id));
        // Stuff for the gamification like level, exp and the monster information
        model.addAttribute("Lvl", userService.getCurrentUser().getLvl());
        model.addAttribute("Exp", userService.getCurrentUser().getExp());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        //-------------------------------------------------------------------------------------------------

        return "projectoverview/editProject";
    }

    /**
     * Processes form for editing an existing project
     *
     * @param project project with changed values
     * @param result  contains (if present) errors of project validation
     * @param model   contains the edited project
     * @return redirect to projectoverview
     */
    @PostMapping("editProject")
    public String processDisplayEditForm(@Valid @ModelAttribute Project project, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("title", "Edit Project");
            return "projectoverview/editProject";
        }

        // saving edited project
        projectService.save(project);

        return "redirect:/projectoverview";
    }

    /**
     * Removes current user from selected project
     *
     * @param id id of project
     * @return redirect to projectoverview
     */
    @GetMapping("leaveProject{projectId}")
    public String leaveProject(@RequestParam("projectId") Integer id, Model model) {
        // Stuff for the gamification like level, exp and the monster information
        model.addAttribute("Lvl", userService.getCurrentUser().getLvl());
        model.addAttribute("Exp", userService.getCurrentUser().getExp());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        //-------------------------------------------------------------------------------------------------

        userService.getCurrentUser().removeProject(projectService.findById(id));

        // saving changes to database
        userService.saveUser(userService.getCurrentUser());

        return "redirect:/projectoverview";
    }

    /**
     * Shows all users that are part of a project
     *
     * @param id    the project id
     * @param model contains data needed to view all users
     * @return users.html
     */
    @GetMapping("users{projectId}")
    public String displayUsersForm(@RequestParam("projectId") Integer id, Model model) {

        model.addAttribute("title", "Users");
        model.addAttribute("users", projectService.findById(id).getUsers());
        model.addAttribute("user", new User());
        model.addAttribute("project", id);
        // Stuff for the gamification like level, exp and the monster information
        model.addAttribute("Lvl", userService.getCurrentUser().getLvl());
        model.addAttribute("Exp", userService.getCurrentUser().getExp());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        //-------------------------------------------------------------------------------------------------

        return "projectoverview/users";
    }

    /**
     * Processes request for adding a user to project
     *
     * @param id     project id of current project
     * @param user   user object that contains the email from the form
     * @param result contains (if present) errors of validation
     * @param model  contains data from the view
     * @return users.html
     */
    @PostMapping("users{projectId}")
    public String processDisplayUsersForm(@RequestParam("projectId") Integer id, @Valid @ModelAttribute User user,
                                          BindingResult result, Model model) {

        // Checking if user exists and is already part of project
        projectAddUserValidator.validate(new AddUserDTO(user, id), result);

        if (result.hasErrors()) {
            model.addAttribute("title", "Users");
            model.addAttribute("users", projectService.findById(id).getUsers());
            model.addAttribute("project", id);
            // Stuff for the gamification like level, exp and the monster information
            model.addAttribute("Lvl", userService.getCurrentUser().getLvl());
            model.addAttribute("Exp", userService.getCurrentUser().getExp());
            model.addAttribute("Surname", userService.getCurrentUser().getSurname());
            model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
            model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
            model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
            model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
            model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
            //-------------------------------------------------------------------------------------------------

            return "projectoverview/users";
        }

        projectService.findById(id).addUser(userService.getUserByEmail(user.getEmail()));

        // saving changes to database
        projectService.save(projectService.findById(id));

        return "redirect:/projectoverview/users?projectId=" + id;
    }

    /**
     * Deletes specific user from current project
     *
     * @param userId    user that needs to be removed
     * @param projectId id of current project
     * @return redirect to users.html
     */
    @GetMapping("users/delete{projectId, userId}")
    public String processDeleteUser(@RequestParam("userId") Integer userId, @RequestParam("projectId") Integer projectId) {

        projectService.findById(projectId).removeUser(userService.getUserById(userId));

        // saving changes to database
        projectService.save(projectService.findById(projectId));

        return "redirect:/projectoverview/users?projectId=" + projectId;
    }

    /**
     * sets the hp for the monster
     *
     * @return redirect to users.html
     */
    @GetMapping("/decreseHp{MonsterId}")
    public String updateHp(@RequestParam("MonsterId") Integer monsterId) {

        monsterService.findMonsterById(monsterId).setCurrentLifePoints(450);

        // saving changes to database
        monsterService.saveMonster(monsterService.findMonsterById(monsterId));

        return "redirect:";
    }

}