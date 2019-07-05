package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Comment;
import de.beatyourtask.beatyourtask.model.Task;
import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.CommentService;
import de.beatyourtask.beatyourtask.services.TaskService;
import de.beatyourtask.beatyourtask.services.UserService;
import de.beatyourtask.beatyourtask.validators.AddUserDTO;
import de.beatyourtask.beatyourtask.validators.CommentDTO;
import de.beatyourtask.beatyourtask.validators.TaskAddAssigneeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

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
    TaskAddAssigneeValidator taskAddAssigneeValidator;

    @Autowired
    CommentService commentService;

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

    /**
     * Removes specific user from current task as assignee
     * @param taskId id of current task
     * @param userId user that needs to be removed
     * @return redirect to assignees
     */
    @GetMapping("removeassignee{taskId, userId}")
    public String processRemoveAssignee(@RequestParam("taskId") Integer taskId, @RequestParam("userId") Integer userId){

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
        comment.setMessage(comment.getMessage().replace("\n","<br />"));
        comment.setCreateDate(new Date());

        // saving changes to database
        commentService.save(comment);

        return "redirect:/task/comments?taskId=" + taskId;
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
