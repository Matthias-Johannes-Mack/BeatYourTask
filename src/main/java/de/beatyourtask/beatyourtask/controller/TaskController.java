package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.*;
import de.beatyourtask.beatyourtask.services.*;
import de.beatyourtask.beatyourtask.validators.AddUserDTO;
import de.beatyourtask.beatyourtask.validators.CommentDTO;
import de.beatyourtask.beatyourtask.validators.TaskAddAssigneeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller handlling all requests for /task
 */
@Controller
@RequestMapping(value ="task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    MonsterService monsterService;

    @Autowired
    TaskAddAssigneeValidator taskAddAssigneeValidator;

    @Autowired
    CommentService commentService;

    @Autowired
    LabelService labelService;

    @Autowired
    ProjectService projectService;


    /**
     * Shows all Assignees that are assigned to the task
     * @param taskId the task id
     * @param model contains data needed to view all assignees
     * @return assignees.html
     */
    @GetMapping("assignees{taskId}")
    public String displayAssigneesForm(@RequestParam("taskId") Integer taskId, Model model) {

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();

        // filling the model
        model.addAttribute("title", "Assignees");
        model.addAttribute("users", task.getAssignees());
        model.addAttribute("user", new User());
        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", projectId);


        return "task/assignees";
    }

    /**
     * Processes request for adding a assignee to the task
     * @param taskId task id of current task
     * @param user the user to be added as assignee
     * @param result contains (if present) errors of user validation
     * @param model contains data for the view
     * @return redirect to assignees
     */
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

    /**
     * Removes specific user from current task as assignee
     * @param taskId id of current task
     * @param userId user that needs to be removed
     * @return redirect to assignees
     */
    @GetMapping("removeassignee{taskId, userId}")
    public String processRemoveAssignee(@RequestParam("taskId") Integer taskId, @RequestParam("userId") Integer userId) {

        taskService.getTaskById(taskId).removeUser(userService.getUserById(userId));

        // saving changes to database
        taskService.saveTask(taskService.getTaskById(taskId));

        return "redirect:/task/assignees?taskId=" + taskId;
    }


    /**
     * Shows all comments for the task
     * @param taskId the id of the current task
     * @param model hold data needed to display the view
     * @return comments.html
     */
    @GetMapping("comments{taskId}")
    public String displayComments(@RequestParam("taskId") Integer taskId, Model model) {

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();


        model.addAttribute("title", "Comments");
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", commentService.findAllByTask(task));
        model.addAttribute("taskId", taskId);
        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("projectId", projectId);


        return "task/comments";
    }

    /**
     * Creates a new comment
     * @param taskId id of current task
     * @param comment comment object from the form
     * @return redirect to comments
     */
    @PostMapping("comments{taskId}")
    public String processCommentForm(@RequestParam("taskId") Integer taskId, @ModelAttribute Comment comment){

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();

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

    @GetMapping("editLabel{taskId, labelId}")
    public String displayEditLabelForm(@RequestParam("taskId") Integer taskId,@RequestParam("labelId") Integer labelId, Model model) {

        Task task = taskService.getTaskById(taskId);
        Integer projectId = task.getTasklist().getProject().getProjectId();

        model.addAttribute("title", "Labels");
        model.addAttribute("labels", task.getLabels());
        model.addAttribute("label", new Label());
        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", projectId);


        return "task/addLabel";
    }

    @PostMapping("editLabel{taskId, labelId}")
    public String processEditLabelForm(@RequestParam("taskId") Integer taskId,@RequestParam("labelId") Integer labelId, @ModelAttribute @Valid Label label,
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

        labelService.getLabelById(labelId).setLabelName(label.getLabelName());
        labelService.getLabelById(labelId).setLabelColor(label.getLabelColor());


        System.out.println("Task-Labels: " + taskService.getTaskById(taskId).getLabels());
        System.out.println("Project-Labels: " + projectService.findById(projectId).getLabel());

        // saving changes to database
        taskService.saveTask(taskService.getTaskById(taskId));
        projectService.save(projectService.findById(projectId));

        return "redirect:/task/labels?taskId=" + taskId;
    }

    @GetMapping("removeLabelPerm{taskId, labelId}")
    public String processRemoveLabelPerm(@RequestParam("taskId") Integer taskId, @RequestParam("labelId") Integer labelId) {
        Integer projectId =  taskService.getTaskById(taskId).getTasklist().getProject().getProjectId();

        System.out.println("TaskId: " + taskId);
        System.out.println("LabelId: " + labelId);


        for (Tasklist tasklists : projectService.findById(projectId).getLists()) {
            for (Task tasks : tasklists.getTasks()) {
                tasks.removeLabel(labelService.getLabelById(labelId));
                taskService.saveTask(tasks);
            }
        }
        taskService.getTaskById(taskId).getTasklist().getProject().removeLabel(labelService.getLabelById(labelId));

        // saving changes to database
        taskService.saveTask(taskService.getTaskById(taskId));
        projectService.save(projectService.findById(projectId));


        return "redirect:/task/labels?taskId=" + taskId;
    }


    /**
     * Removes Comment from current task
     * @param taskId id of current task
     * @param commentId id of comment to be removed
     * @return redirect to comments
     */
    @GetMapping("removecomment{taskId, commentId}")
    public String processRemoveComment(@RequestParam Integer taskId, @RequestParam Integer commentId){

        commentService.delete(commentService.findById(commentId));

        return "redirect:/task/comments?taskId=" + taskId;
    }

    /**
     * Processes editing the comment via the modal
     * @param commentDTO Data Transfer Object that holds information about the comment, task and message
     * @return redirect to comments
     */
    @PostMapping("editComment")
    public String editComment(CommentDTO commentDTO){

        Comment editComment = commentService.findById(commentDTO.getCommentId());
        editComment.setMessage(commentDTO.getMessage().replace("\n","<br />"));

        commentService.save(editComment);

        return "redirect:/task/comments?taskId=" + commentDTO.getTaskId();
    }

    /**
     * Returns a JSON representation of an commentDTO object
     * @param commentId id of needed comment
     * @return commentDTO containing information about comment, task and message
     */
    @GetMapping("/findOne")
    @ResponseBody
    public CommentDTO findOne(Integer commentId){

        CommentDTO conComment = new CommentDTO().convert(commentService.findById(commentId));
        return conComment;
    }
}
