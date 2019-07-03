package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Task;
import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.TaskService;
import de.beatyourtask.beatyourtask.services.UserService;
import de.beatyourtask.beatyourtask.validators.AddUserDTO;
import de.beatyourtask.beatyourtask.validators.TaskAddAssigneeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping(value ="task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    TaskAddAssigneeValidator taskAddAssigneeValidator;

    @GetMapping("assignees{taskId}")
    public String displayAssigneesForm(@RequestParam("taskId") Integer taskId, Model model, @RequestHeader(value = "referer", required = false) final String referer) {

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();

        model.addAttribute("title", "Assignees");
        model.addAttribute("users", task.getAssignees());
        model.addAttribute("user", new User());
        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", projectId);

        return "task/assignees";
    }

    @PostMapping("assignees{taskId}")
    public String processDisplayUsersForm(@RequestParam("taskId") Integer taskId, @ModelAttribute @Valid User user,
                                          BindingResult result, Model model){

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();

        // validate user
        taskAddAssigneeValidator.validate(new AddUserDTO(user,taskId), result);

        if(result.hasErrors()){
            model.addAttribute("title", "Assignees");
            model.addAttribute("users", task.getAssignees());
            model.addAttribute("taskId", taskId);
            model.addAttribute("projectId", projectId);
            return "task/assignees";
        }

        taskService.getTaskById(taskId).addUser(userService.getUserByEmail(user.getEmail()));

        // saving changes to database
        taskService.saveTask(taskService.getTaskById(taskId));

        return "redirect:/task/assignees?taskId=" + taskId;
    }

    @GetMapping("removeassignee{taskId, userId}")
    public String processRemoveAssignee(@RequestParam("taskId") Integer taskId, @RequestParam("userId") Integer userId){

        taskService.getTaskById(taskId).removeUser(userService.getUserById(userId));

        // saving changes to database
        taskService.saveTask(taskService.getTaskById(taskId));

        return "redirect:/task/assignees?taskId=" + taskId;
    }
}
