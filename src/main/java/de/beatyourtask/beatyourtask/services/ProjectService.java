package de.beatyourtask.beatyourtask.services;

import de.beatyourtask.beatyourtask.models.Project;
import de.beatyourtask.beatyourtask.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired //Debendency Injection
    private ProjectRepository projectRepository;


    public Project saveProject (Project project){
        return projectRepository.save(project);
    }


}

