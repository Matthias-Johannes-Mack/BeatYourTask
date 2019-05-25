package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.TasklistService;
import de.beatyourtask.beatyourtask.model.Tasklist;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for the ProjectView
 * contains methods for controlling the Projectview
 */
@Controller

public class ProjectviewController {

    @Autowired
    private TasklistService tasklistService;

    @ModelAttribute(value = "newTaskListAttribute")
    public Tasklist newTasklist()
    {

        return new Tasklist();
    }


    /**
     * Loads the Projectview with all tasklists in the databes
     * @param model model containing all the tasklist in the database
     * @return projectview with all tasklist in database
     */
    @GetMapping("/Project")
    public ModelAndView loadLists(Model model) {
        model.addAttribute("tasklists", tasklistService.getAllTaskLists());

        System.out.println("in Get");


        String view = "Projectview";
        return new ModelAndView(view, "command", model);
    }

    /*TODO beim bestätigen des Modals wird micht addList aufgerufen?! */
    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public View addList(@ModelAttribute("newTaskListAttribute") Tasklist newTaskListAttribute) {

        System.out.println("in Post");

        tasklistService.saveList(newTaskListAttribute);


         return new RedirectView("/Project");
    }





}
