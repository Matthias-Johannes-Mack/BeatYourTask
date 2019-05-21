package de.beatyourtask.beatyourtask.controller;


import de.beatyourtask.beatyourtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import de.beatyourtask.beatyourtask.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
/**
 * Controller for the registration / wirefire / Spring 19
 */
public class RegisterController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;


    @Autowired
    public RegisterController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    // Return registration form template
    @RequestMapping(value = "/Register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {
        modelAndView.addObject("User", user);
        modelAndView.setViewName("Register");
        return modelAndView;
    }

    // Process form input data
    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    public String processRegistrationForm(ModelAndView modelAndView, @ModelAttribute("User") User user) {

        // Lookup user in database by e-mail
        User userExists = userService.findByEmail(user.getEmail());

        System.out.println(userExists);

        if (userExists != null) {
           return  "redirect:/?registerfailed";
        }

        // new user so we create user and send confirmation e-mail

        // Disable user until they click on confirmation link in email
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveUser(user);

        return "redirect:/?registersuccessfull";
    }

}
