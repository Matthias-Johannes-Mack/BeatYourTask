package de.beatyourtask.beatyourtask.validators;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.Task;
import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.TaskService;
import de.beatyourtask.beatyourtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;

@Component
public class TaskAddAssigneeValidator implements Validator {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AddUserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddUserDTO addUserDTO = (AddUserDTO) target;
        Task task = taskService.getTaskById(addUserDTO.getId());
        Integer projectId = task.getTasklist().getProject().getProjectId();
        User user = userService.getUserByEmail(addUserDTO.getUser().getEmail());
        List<User> asignees = task.getAssignees();

        // checks if user with email exists
        if(Objects.isNull(user)){
            errors.rejectValue("email","email-not-exist","email doesn´t exist");
        }else{
            // checks if user is already assigned
            if(asignees.contains(user)){
                errors.rejectValue("email","already-assigned","already assigned to task");
            } else{
                // checks if user is part of project
                boolean flag = false;
                for (Project prj: user.getProjects()) {
                    if(prj.getProjectId() == projectId){
                        flag = true;
                    }
                }

                if(!flag){
                    errors.rejectValue("email","not-part-of-project","user not part of project");
                }
            }

        }
    }
}
