package de.beatyourtask.beatyourtask.services;


import de.beatyourtask.beatyourtask.model.*;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.orm.hibernate5.HibernateTemplate;
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

  @Autowired
  private TasklistService tasklistService;

  @Autowired
  private TaskService taskService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    logger.info("Initialisiere Datenbank mit Testdaten...");

    Role userRole = new Role("ROLE_USER");
    roleService.saveRole(userRole);

    Set<Role> userRoles = new HashSet<>();
    userRoles.add(userRole);

    User user1 = new User();
    user1.setPassword(passwordEncoder.encode("password"));
    user1.setEmail("user@test.de");
    user1.setRoles(userRoles);
    userService.saveUser(user1);

    Project sopra = new Project();
    sopra.setProjectName("Testproject 1");
    sopra.setProjectDescription("I´am a Testproject");
    projectService.save(sopra);

    Project seminar = new Project();
    seminar.setProjectName("Testproject 2");
    seminar.setProjectDescription("I´am a Testproject");
    projectService.save(seminar);

    Tasklist liste1 = new Tasklist();
    liste1.setListName("Liste aus DB");
    liste1.setColor("LightGreen");
    tasklistService.saveList(liste1);

    Tasklist liste2 = new Tasklist();
    liste2.setListName("Liste aus DB 2");
    liste2.setColor("SlateBlue");
    tasklistService.saveList(liste2);

    // ----------- Data for Testing Projectoverview ------------------------ //
    Project test_user2 = new Project();
    test_user2.setProjectName("test user2");
    test_user2.setProjectDescription("test for user2");

    User user2 = new User();
    user2.setPassword(passwordEncoder.encode("pw"));
    user2.setEmail("user@angelo");
    user2.setSurname("Max");
    user2.setLastname("Mustermann");
    user2.setRoles(userRoles);
    user2.addProject(test_user2);

    userService.saveUser(user2);


    User user3 = new User();
    user3.setPassword(passwordEncoder.encode("pw"));
    user3.setEmail("us@test");
    user3.setSurname("Maxima");
    user3.setLastname("Musterfrau");
    user3.setRoles(userRoles);

    userService.saveUser(user3);

// ----------- Data for Testing loading tasks ------------------------ //

    Project testPrj = new Project();
    testPrj.setProjectName("testPrj ");
    testPrj.setProjectDescription("test for testPrj");


    User userTest = new User();
    userTest.setPassword(passwordEncoder.encode("password"));
    userTest.setEmail("jan@test");
    userTest.setSurname("Max");
    userTest.setLastname("Mustermann");
    userTest.setRoles(userRoles);
    userTest.addProject(testPrj);
    userService.saveUser(userTest);


    Task task1 = new Task();
    task1.setTaskName("TestTask1");
    taskService.saveTask(task1);

    Task task2 = new Task();
    task2.setTaskName("TestTask2");
    taskService.saveTask(task2);

    Tasklist tasklist1 = new Tasklist();
    tasklist1.setListName("tasklist1");
    tasklistService.saveList(tasklist1);

    tasklist1.addTasks(task1);
    tasklistService.saveList(tasklist1);

    Tasklist tasklist2 = new Tasklist();
    tasklist2.setListName("tasklist2");
    tasklistService.saveList(tasklist2);

    tasklist2.addTasks(task2);
    tasklistService.saveList(tasklist2);

    projectService.save(testPrj);

    testPrj.addTasklist(tasklist1);
    testPrj.addTasklist(tasklist2);
    projectService.save(testPrj);
  }
}
