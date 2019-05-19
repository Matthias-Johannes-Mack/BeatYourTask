package de.beatyourtask.beatyourtask.services;


import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.Role;
import de.beatyourtask.beatyourtask.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInitialiser implements ApplicationListener<ContextRefreshedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(DatabaseInitialiser.class);

  @Autowired
  private ProjectService projectService;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    logger.info("Initialisiere Datenbank mit Testdaten...");

    Role userRole = new Role("ROLE_USER");
    roleService.saveRole(userRole);

    Set<Role> userRoles = new HashSet<>();
    userRoles.add(userRole);



    User user1 = new User();
    user1.setUsername("user");
    user1.setPassword(passwordEncoder.encode("password"));
    user1.setEmail("st154919@stud.uni-stuttgart.de");
    user1.setRoles(userRoles);
    userService.saveUser(user1);



    Project sopra = new Project();
    sopra.setProjectName("Softwarepraktikum");
    projectService.saveProject(sopra);

    Project seminar = new Project();
    seminar.setProjectName("Seminar");
    projectService.saveProject(seminar);


  }
}