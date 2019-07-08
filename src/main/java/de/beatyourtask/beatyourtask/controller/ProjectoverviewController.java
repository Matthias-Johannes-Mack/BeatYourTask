package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.LevelService;
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
    LevelService levelService;

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
        model.addAttribute("maxExp", userService.getCurrentUser().getMaxExp());
        model.addAttribute("lvlPercent", userService.getCurrentUser().getLvlPercentage());
        model.addAttribute("Damage", userService.getCurrentUser().getDamage());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        model.addAttribute("percentage", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getPercentageLeft());
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
        model.addAttribute("maxExp", userService.getCurrentUser().getMaxExp());
        model.addAttribute("lvlPercent", userService.getCurrentUser().getLvlPercentage());
        model.addAttribute("Damage", userService.getCurrentUser().getDamage());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        model.addAttribute("percentage", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getPercentageLeft());
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
            model.addAttribute("maxExp", userService.getCurrentUser().getMaxExp());
            model.addAttribute("lvlPercent", userService.getCurrentUser().getLvlPercentage());
            model.addAttribute("Damage", userService.getCurrentUser().getDamage());
            model.addAttribute("Surname", userService.getCurrentUser().getSurname());
            model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
            model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
            model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
            model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
            model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
            model.addAttribute("percentage", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getPercentageLeft());
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
        model.addAttribute("maxExp", userService.getCurrentUser().getMaxExp());
        model.addAttribute("Damage", userService.getCurrentUser().getDamage());
        model.addAttribute("lvlPercent", userService.getCurrentUser().getLvlPercentage());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        model.addAttribute("percentage", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getPercentageLeft());
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
        model.addAttribute("maxExp", userService.getCurrentUser().getMaxExp());
        model.addAttribute("lvlPercent", userService.getCurrentUser().getLvlPercentage());
        model.addAttribute("Damage", userService.getCurrentUser().getDamage());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        model.addAttribute("percentage", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getPercentageLeft());
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
        model.addAttribute("maxExp", userService.getCurrentUser().getMaxExp());
        model.addAttribute("Damage", userService.getCurrentUser().getDamage());
        model.addAttribute("lvlPercent", userService.getCurrentUser().getLvlPercentage());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        model.addAttribute("percentage", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getPercentageLeft());
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
            model.addAttribute("maxExp", userService.getCurrentUser().getMaxExp());
            model.addAttribute("Damage", userService.getCurrentUser().getDamage());
            model.addAttribute("lvlPercent", userService.getCurrentUser().getLvlPercentage());
            model.addAttribute("Surname", userService.getCurrentUser().getSurname());
            model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
            model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
            model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
            model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
            model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
            model.addAttribute("percentage", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getPercentageLeft());
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
    @RequestMapping("decreaseHp{MonsterId}")
    public String updateHp(@RequestParam("MonsterId") Integer monsterId) {
        int cPoints = monsterService.findMonsterById(monsterId).getCurrentLifePoints();
        int damage = userService.getCurrentUser().getDamage();

        // if there are enough damage points
        if ((cPoints - damage) >= 0) {
            // put damage to the monster
            monsterService.findMonsterById(monsterId).setCurrentLifePoints(monsterService.findMonsterById(monsterId).getCurrentLifePoints() - damage);
            // calculates the value for the percentage
            int currentP = monsterService.findMonsterById(monsterId).getCurrentLifePoints() - damage;
            int maxP = monsterService.findMonsterById(monsterId).getLifepoints();
            int calculation = (100 * currentP) / maxP;
            if (calculation < 0) {
                calculation = 0;
            }
            monsterService.findMonsterById(monsterId).setPercentageLeft(calculation);
        } else {
            // puts monster to the next lvl if its level is under 3
            if (userService.getCurrentUser().getActiveMonsterId() <= 2) {
                //set the new monster and save it
                userService.getAllUsers().forEach((u) -> userService.getUserById(u.getId()).setActiveMonsterId(userService.getCurrentUser().getActiveMonsterId() + 1));
                userService.saveUser(userService.findByEmail(userService.getCurrentUser().getEmail()));
                //puts the damage  to the old monster and the remaining damage to the new one
                int restDamage = (cPoints - damage) * (-1);
                System.out.println(restDamage);
                monsterService.findMonsterById(monsterId).setCurrentLifePoints(monsterService.findMonsterById(monsterId).getCurrentLifePoints() - (damage - restDamage));
                monsterService.findMonsterById(monsterId + 1).setCurrentLifePoints(monsterService.findMonsterById(monsterId + 1).getCurrentLifePoints() - restDamage);

                // calculates the value for the percentage
                int currentP = monsterService.findMonsterById(monsterId + 1).getCurrentLifePoints() - restDamage;
                int maxP = monsterService.findMonsterById(monsterId + 1).getLifepoints();
                // int calculation = (100 * currentP) / maxP;


                monsterService.findMonsterById(monsterId + 1).setPercentageLeft(100);
                // resets the new monster picture
                if ((userService.getCurrentUser().getActiveMonsterId() + 1) < 4) {
                    String currentMonsterName = monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId() + 1).getMonsterName();
                    monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId() + 1).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_000.png");
                }
            } else {
                // set all user the monster to 1
                userService.getAllUsers().forEach((u) -> userService.getUserById(u.getId()).setActiveMonsterId(1));


                // resets all monster with higher values
                monsterService.findMonsterById(1).setCurrentLifePoints(2500);
                monsterService.findMonsterById(1).setLifepoints(2500);
                monsterService.findMonsterById(1).setPercentageLeft(100);

                monsterService.findMonsterById(2).setCurrentLifePoints(3000);
                monsterService.findMonsterById(2).setLifepoints(3000);
                monsterService.findMonsterById(2).setPercentageLeft(100);

                monsterService.findMonsterById(3).setCurrentLifePoints(3500);
                monsterService.findMonsterById(3).setLifepoints(3500);
                monsterService.findMonsterById(3).setPercentageLeft(100);

                // save the whole thing
                userService.saveUser(userService.findByEmail(userService.getCurrentUser().getEmail()));
            }
        }
        // saving changes to database
        monsterService.saveMonster(monsterService.findMonsterById(monsterId));
        // set the monsterpic
        double percentage = monsterService.findMonsterById(monsterId).getPercentageLeft();
        String currentMonsterName = monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterName();
        if (percentage <= 100 && percentage > 90) {
            monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_000.png");
        }

        if (percentage <= 90 && percentage > 80) {
            monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_003.png");
        }

        if (percentage <= 80 && percentage > 70) {
            monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_003.png");
        }
        if (percentage <= 70 && percentage > 60) {
            monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_004.png");
        }
        if (percentage <= 60 && percentage > 50) {
            monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_005.png");
        }
        if (percentage <= 50 && percentage > 40) {
            monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_006.png");
        }
        if (percentage <= 40 && percentage > 30) {
            monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_007.png");
        }
        if (percentage <= 30 && percentage > 20) {
            monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_009.png");
        }
        if (percentage <= 20 && percentage > 10) {
            monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_010.png");
        }
        if (percentage <= 10 && percentage > 0) {
            monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).setMonsterPic("/images/Monster/" + currentMonsterName + "/0_" + currentMonsterName + "_Dying_014.png");
        }

        // save the whole thing
        monsterService.saveMonster(monsterService.findMonsterById(monsterId));

        // section for the level work
        int userExp = userService.getCurrentUser().getExp();
        int currentUserLvl = userService.getCurrentUser().getLvl();
        int maxLvlExp = levelService.findLevelById(currentUserLvl).getMaxExpLvl();
        // if the user Exp is to high, change the level
        if ((userExp + 100) > maxLvlExp) {
            if (currentUserLvl <= 10) {

                // increase the lvl
                userService.getCurrentUser().setLvl(currentUserLvl + 1);
                userService.getCurrentUser().setExp(0);
                userService.getCurrentUser().setLvlPercentage(0);
                userService.getCurrentUser().setMaxExp(levelService.findLevelById(currentUserLvl + 1).getMaxExpLvl());
                userService.getCurrentUser().setDamage(levelService.findLevelById(currentUserLvl + 1).getDamage());
            }
        } else {
            // increse the exp
            userService.getCurrentUser().setExp(userExp + 100);
            // calculate the percentage for the Progressbar
            int calc = ((100 * userExp) / maxLvlExp);
            userService.getCurrentUser().setLvlPercentage(calc);
        }
        // save the user
        userService.saveUser(userService.getCurrentUser());
        return "redirect:/projectoverview";
    }
}