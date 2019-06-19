package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.ProjectService;
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

    @Autowired
    private ProjectService projectService;


    @ModelAttribute(value = "newTaskListAttribute")
    public Tasklist newTasklist()
    {

        return new Tasklist();
    }



    /**
     * Loads the Projectview with the responding tasklists in the database
     * @param model model containing all the tasklist in the database
     * @return projectview with all tasklist in database
     * */
    @GetMapping("/Project{ProjectId}")
    public ModelAndView loadListsID(Model model, @RequestParam("ProjectId") Integer id) {
        model.addAttribute("tasklists", projectService.findById(id).getLists());
        System.out.println(id);
        //Project?ProjectId=10
        System.out.println("in /ProjectID");


        String view = "Projectview";
        return new ModelAndView(view, "command", model);
    }


    /**
     * Saves a new list in the database
     * @param newTaskListAttribute new Tasklist Object from View
     * @return reirekt to Projekt
     */
    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public String addList(@ModelAttribute("newTaskListAttribute") Tasklist newTaskListAttribute, @RequestHeader(value = "referer", required = false) final String referer) {

        System.out.println("in add List");
        String projectId = referer.substring(referer.indexOf("=") + 1, referer.length());
        System.out.println("ProjectId: "+projectId);
        tasklistService.saveList(newTaskListAttribute);
        System.out.println("saved tasklist");
        System.out.println("saved tasklist with id: "+newTaskListAttribute.getListId());



        try {
            System.out.println("In try");
            Integer projectIdInt = Integer.parseInt(projectId);



            projectService.findById(projectIdInt).addTasklist(tasklistService.loadTasklistById(newTaskListAttribute.getListId()));

            projectService.save(projectService.findById(projectIdInt));

        } catch (NumberFormatException e){
            e.printStackTrace();
        }




       // return new RedirectView("/Project");
        return "redirect:/Project?ProjectId="+projectId;

    }

    /**
     * Edit a list in the database
     * @param newTaskListAttribute new Tasklist Object from View
     * @return reirekt to Projekt
     */
    @RequestMapping(value = "/editList", method = RequestMethod.POST)
    public View editList(@ModelAttribute("newTaskListAttribute") Tasklist newTaskListAttribute) {
        int id = newTaskListAttribute.getListId();
        System.out.println(id);
        System.out.println("in Edit");
        System.out.println(newTaskListAttribute.getColor());
        Tasklist list = tasklistService.loadTasklistById(id);
        String color=newTaskListAttribute.getColor().replaceFirst("background-color:","");
        System.out.println(color);
        list.setColor(color);
        list.setListName(newTaskListAttribute.getListName());
        tasklistService.saveList(list);



        return new RedirectView("/Project");
    }


    /**
     * Deletes a list in the database
     * @param newTaskListAttribute new Tasklist Object from View
     * @return reirekt to Projekt
     */
    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    public View deleteList(@ModelAttribute("newTaskListAttribute") Tasklist newTaskListAttribute) {
        int id = newTaskListAttribute.getListId();
        tasklistService.deleteTasklistById(id);
        return new RedirectView("/Project");
    }




}
