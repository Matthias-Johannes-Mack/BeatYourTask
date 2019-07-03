package de.beatyourtask.beatyourtask.validators;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * Class for Validation of the addUser form
 */
@Component
public class ProjectAddUserValidator implements Validator {


    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AddUserDTO.class.isAssignableFrom(clazz);
    }

    /**
     * Validation of the input data from the addUser form
     * @param target addUserDTO object that contains email and projectId
     * @param errors contains errors of validation
     */
    @Override
    public void validate(Object target, Errors errors) {

        AddUserDTO addUserDTO = (AddUserDTO) target;
        User user = userService.getUserByEmail(addUserDTO.getUser().getEmail());

        // checks if user with email exists
        if(Objects.isNull(user)){
            errors.rejectValue("email","email-not-exist","email doesn´t exist");
        }else{
            // checks if user is already part of the project
            boolean flag = false;
            for (Project prj: user.getProjects()) {
                if(prj.getProjectId() == addUserDTO.getId()){
                    flag = true;
                }
            }
            if(flag){
                errors.rejectValue("email","part-of-project","already part of project");
            }
        }


    }
}
