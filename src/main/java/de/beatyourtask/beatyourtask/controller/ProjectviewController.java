package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.TasklistService;
import de.beatyourtask.beatyourtask.model.Tasklist;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

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
     * Loads the Projectview with all tasklists in the database
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


    /**
     * Saves a new list in the database
     * @param newTaskListAttribute new Tasklist Object from View
     * @return reirekt to Projekt
     */
    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public View addList(@ModelAttribute("newTaskListAttribute") Tasklist newTaskListAttribute) {

        System.out.println("in Post");

        tasklistService.saveList(newTaskListAttribute);

        return new RedirectView("/Project");
    }

    @GetMapping("editList{listId}")
    public String displayEditForm(@RequestParam("listId") Integer id, Model model){

        model.addAttribute("title", "Edit List");
        model.addAttribute("list", tasklistService.loadTasklistById(id));

        return "/editList";
    }

    /**
     * Processes form for editing an existing project
     * @param list tasklist with changed values
     * @param result contains (if present) errors of project validation
     * @param model contains the edited project
     * @return redirect to projectoverview
     */
    @PostMapping("editList")
    public View processDisplayEditForm(@Valid @ModelAttribute Tasklist list, BindingResult result, Model model){

        /**
         if(result.hasErrors()){
         model.addAttribute("list",list);
         model.addAttribute("title", "Edit List");
         return "/editList";
         }
         */

        // saving edited project
        tasklistService.saveList(list);

        return new RedirectView("/Project");
    }

    /**
     * Removes current user from selected project
     * @param id id of project
     * @return redirect to projectoverview
     */
    @GetMapping("deleteList{listId}")
    public View deleteList(@RequestParam("listId") Integer id){

        tasklistService.deleteTasklistById(id);

        return new RedirectView("/Project");
    }




}
