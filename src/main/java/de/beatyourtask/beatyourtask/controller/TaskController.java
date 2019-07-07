package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.*;
import de.beatyourtask.beatyourtask.services.*;
import de.beatyourtask.beatyourtask.validators.AddUserDTO;
import de.beatyourtask.beatyourtask.validators.TaskAddAssigneeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value ="task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    TaskAddAssigneeValidator taskAddAssigneeValidator;

    @Autowired
    CommentService commentService;

    @Autowired
    LabelService labelService;

    @Autowired
    ProjectService projectService;


    @GetMapping("assignees{taskId}")
    public String displayAssigneesForm(@RequestParam("taskId") Integer taskId, Model model) {

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
                                          BindingResult result, Model model) {

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();

        // validate user
        taskAddAssigneeValidator.validate(new AddUserDTO(user, taskId), result);

        if (result.hasErrors()) {
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
    public String processRemoveAssignee(@RequestParam("taskId") Integer taskId, @RequestParam("userId") Integer userId) {

        taskService.getTaskById(taskId).removeUser(userService.getUserById(userId));

        // saving changes to database
        taskService.saveTask(taskService.getTaskById(taskId));

        return "redirect:/task/assignees?taskId=" + taskId;
    }

    @GetMapping("comments{taskId}")
    public String displayComments(@RequestParam("taskId") Integer taskId, Model model, @RequestHeader(value = "referer", required = false) final String referer) {

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();


        model.addAttribute("title", "Comments");
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", commentService.findAllByTask(task));
        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", projectId);

        return "task/comments";
    }


    @PostMapping("comments{taskId}")
    public String processDisplayUsersForm(@RequestParam("taskId") Integer taskId, @ModelAttribute @Valid Comment comment, BindingResult result, Model model) {

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();

        if (result.hasErrors()) {
            model.addAttribute("title", "Comments");
            model.addAttribute("comments", commentService.findAllByTask(task));
            model.addAttribute("taskId", taskId);
            model.addAttribute("projectId", projectId);
            return "task/comments";
        }

        comment.setAuthor(userService.getCurrentUser());
        comment.setOwnerTask(taskService.getTaskById(taskId));
        comment.setMessage(comment.getMessage().replace("\n", "<br />"));
        comment.setCreateDate(new Date());

        // saving changes to database
        commentService.save(comment);

        return "redirect:/task/comments?taskId=" + taskId;
    }


    /**
     * Shows all the labels linked to the task itself and the labels
     * @param taskId
     * @param model
     * @return
     */
    @RequestMapping(value="labels{taskId}", method=RequestMethod.GET)
    public String displayLabelsForm(@RequestParam("taskId") Integer taskId, Model model) {

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();

        List<Label> labels = new ArrayList<Label>();
        labels = task.getTasklist().getProject().getLabel();
        labels.removeAll(task.getLabels());

        model.addAttribute("title", "Labels");
        model.addAttribute("tasklabels", task.getLabels());
        model.addAttribute("projectlabels", labels);
        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", projectId);

        return "task/labels";
    }

    @GetMapping("removelabel{taskId, labelId}")
    public String processRemoveLabel(@RequestParam("taskId") Integer taskId, @RequestParam("labelId") Integer labelId) {

        System.out.println("TaskId: " + taskId);
        System.out.println("LabelId: " + labelId);

        taskService.getTaskById(taskId).removeLabel(labelService.getLabelById(labelId));

        // saving changes to database
        taskService.saveTask(taskService.getTaskById(taskId));


        return "redirect:/task/labels?taskId=" + taskId;
    }

    @GetMapping("addlabel{taskId, labelId}")
    public String processAddLabel(@RequestParam("taskId") Integer taskId, @RequestParam("labelId") Integer labelId) {

        taskService.getTaskById(taskId).addLabel(labelService.getLabelById(labelId));

        // saving changes to database
        taskService.saveTask(taskService.getTaskById(taskId));

        return "redirect:/task/labels?taskId=" + taskId;
    }

    @GetMapping("addLabel{taskId}")
    public String displayAddLabelForm(@RequestParam("taskId") Integer taskId, Model model) {

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();

        model.addAttribute("title", "Labels");
        model.addAttribute("labels", task.getLabels());
        model.addAttribute("label", new Label());
        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", projectId);

        return "task/addLabel";
    }

    @PostMapping("addLabel{taskId}")
    public String processAddLabelForm(@RequestParam("taskId") Integer taskId, @ModelAttribute @Valid Label label,
                                          BindingResult result, Model model) {

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();


        if (result.hasErrors()) {
            model.addAttribute("title", "Labels");
            model.addAttribute("users", task.getLabels());
            model.addAttribute("taskId", taskId);
            model.addAttribute("projectId", projectId);
            return "task/addLabel";
        }


        taskService.getTaskById(taskId).addLabel(label);
        projectService.findById(projectId).setLabel(label);

        System.out.println("Task-Labels: " + taskService.getTaskById(taskId).getLabels());
        System.out.println("Project-Labels: " + projectService.findById(projectId).getLabel());

        // saving changes to database
        taskService.saveTask(taskService.getTaskById(taskId));
        projectService.save(projectService.findById(projectId));

        return "redirect:/task/labels?taskId=" + taskId;
    }
}
