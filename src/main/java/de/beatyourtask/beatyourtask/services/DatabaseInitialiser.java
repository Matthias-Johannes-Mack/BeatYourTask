package de.beatyourtask.beatyourtask.services;


import de.beatyourtask.beatyourtask.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Inizialises the database | wirefire | Summer ´19
 */
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
    private MonsterService monsterService;

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
        user1.setSurname("Max");
        user1.setLastname("Mustermann");
        user1.setRoles(userRoles);
        user1.setExp(500);
        user1.setLvl(1);
        user1.setActiveMonsterId(1);
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

        //Add Monster
        Monster monster1 = new Monster();
        monster1.setMonsterId(1);
        monster1.setLifepoints(500);
        monster1.setCurrentLifePoints(500);
        monster1.setMonsterPic("images/Monster/Ogre/0_Ogre_Dying_000.png");
        monsterService.saveMonster(monster1);

        Monster monster2 = new Monster();
        monster2.setMonsterId(2);
        monster2.setLifepoints(1000);
        monster2.setCurrentLifePoints(1000);
        monster2.setMonsterPic("images/Monster/Goblin/0_Goblin_Dying_000.png");
        monsterService.saveMonster(monster2);

        Monster monster3 = new Monster();
        monster3.setMonsterId(3);
        monster3.setLifepoints(1500);
        monster3.setCurrentLifePoints(1500);
        monster3.setMonsterPic("images/Monster/Orc/0_Orc_Dying_000.png");
        monsterService.saveMonster(monster3);

    }
}
