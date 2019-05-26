package de.beatyourtask.beatyourtask.controller;


import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
/**
 * Controller for the Project Creation / wirefire / Spring 19
 */
public class CreateProjectController {


    @Autowired
    private ProjectService projectService;


 // is this code needed?
//    @Autowired
//    public CreateProjectController(ProjectService projectService) {
//        this.projectService = this.projectService;
//    }


    // Process form input data
    @RequestMapping(value = "/Projectoverview", method = RequestMethod.POST)
    public String processRegistrationForm(ModelAndView modelAndView, @ModelAttribute("Project") Project project) {

    projectService.saveProject(project);
        return "redirect:/?registersuccessfull";
    }

}
