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

    @GetMapping("comments{taskId}")
    public String displayComments(@RequestParam("taskId") Integer taskId, Model model, @RequestHeader(value = "referer", required = false) final String referer) {

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


    @PostMapping("comments{taskId}")
    public String processDisplayUsersForm(@RequestParam("taskId") Integer taskId, @ModelAttribute Comment comment, Model model){

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


    @GetMapping("removecomment{taskId, commentId}")
    public String processRemoveComment(@RequestParam Integer taskId, @RequestParam Integer commentId){

        commentService.delete(commentService.findById(commentId));

        return "redirect:/task/comments?taskId=" + taskId;
    }

    @PostMapping("editComment")
    public String editComment(CommentDTO commentDTO){

        Comment editComment = commentService.findById(commentDTO.getCommentId());
        editComment.setMessage(commentDTO.getMessage().replace("\n","<br />"));

        commentService.save(editComment);

        return "redirect:/task/comments?taskId=" + commentDTO.getTaskId();
    }

    @GetMapping("/findOne")
    @ResponseBody
    public CommentDTO findOne(Integer commentId){

        CommentDTO conComment = new CommentDTO().convert(commentService.findById(commentId));

        return conComment;
    }
}
