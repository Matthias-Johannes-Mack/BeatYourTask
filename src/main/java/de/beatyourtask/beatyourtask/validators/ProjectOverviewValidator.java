package de.beatyourtask.beatyourtask.validators;

import de.beatyourtask.beatyourtask.model.Project;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProjectOverviewValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Project.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectName", "nameEmpty", "Name is empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"projectDescription", "descriptionEmpty", "Description is empty!");
    }
}
