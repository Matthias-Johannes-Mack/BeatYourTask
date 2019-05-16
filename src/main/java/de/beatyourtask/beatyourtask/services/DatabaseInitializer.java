package de.beatyourtask.beatyourtask.services;

import de.beatyourtask.beatyourtask.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ProjectService projectService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Project sopra = new Project();
        sopra.setProjectName("Softwarepraktikum");
        projectService.saveProject(sopra);

        Project seminar = new Project();
        seminar.setProjectName("seminar");
        projectService.saveProject(seminar);
    }
}
