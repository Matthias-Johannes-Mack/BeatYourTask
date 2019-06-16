package de.beatyourtask.beatyourtask.validators;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class ProjectAddUserValidator implements Validator {


    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AddUserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        AddUserDTO addUserDTO = (AddUserDTO) target;

        // Checks if user with email exists
        if(Objects.isNull(userService.findByEmail(addUserDTO.getUser().getEmail()))){
            errors.rejectValue("email","email-not-exist","email doesn´t exist");
        }else{

            // Checks if user is already part of the project
            User user = userService.getUserByEmail(addUserDTO.getUser().getEmail());

            boolean flag = false;
            for (Project prj: user.getProjects()) {
                if(prj.getProjectId() == addUserDTO.getProjectId()){
                    flag = true;
                }
            }

            if(flag){
                errors.rejectValue("email","part-of-project","user already part of project");
            }
        }


    }
}
