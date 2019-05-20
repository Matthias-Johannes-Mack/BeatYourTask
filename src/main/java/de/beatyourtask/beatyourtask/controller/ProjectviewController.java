package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.services.TasklistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller for the ProjectView
 * contains methods for controlling the Projectview
 */
@Controller
public class ProjectviewController {

    @Autowired
    private TasklistService tasklistService;

    /**
     * Loads the Projectview with all tasklists in the databes
     * @param model model containing all the tasklist in the database
     * @return projectview with all tasklist in database
     */
    @GetMapping("/Project/")
    public String loadLists(Model model) {
        model.addAttribute("tasklists", tasklistService.getAllTaskLists());
        return "Projektansicht";
    }
}
