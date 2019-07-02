package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("taskAssignees{taskId}")
    public String displayAssigneesForm(@RequestParam("taskId") Integer taskId, Model model, @RequestHeader(value = "referer", required = false) final String referer) {

        Integer projectId =  Integer.parseInt(referer.substring(referer.indexOf("=") + 1, referer.length()));

        System.out.println("----------------IN ASSIGNEES--------------------");
        System.out.println(model.asMap());
        System.out.println(projectId );


        model.addAttribute("title", "Assignees");
        model.addAttribute("users", taskService.getTaskById(taskId).getAssignees());
        model.addAttribute("user", new User());
        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", projectId);



        return "task/assignees";
    }
}
