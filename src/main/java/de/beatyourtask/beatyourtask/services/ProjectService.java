package de.beatyourtask.beatyourtask.services;


import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectService() {
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

        public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Project findById(int projectId){
        return projectRepository.findById(projectId).get();
    }


}
