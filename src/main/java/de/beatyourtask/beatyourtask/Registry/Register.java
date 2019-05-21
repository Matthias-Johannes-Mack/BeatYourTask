package de.beatyourtask.beatyourtask.Registry;

import de.beatyourtask.beatyourtask.model.User;
import de.beatyourtask.beatyourtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class for the Registration / wirefire / Spring 19
 */
public class Register {
    // enable the passwordencoder
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    // to save the create user
    @Autowired
    private UserService userService;



}
