package de.beatyourtask.beatyourtask.services;


import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  Class for handling projects in the database
 */
@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectService() {
    }

    /**
     * saves a project in the database
     * @param project project to be saved
     * @return
     */
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    /**
     * gets all existing Projects
     * @return List of all project
     */
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    /**
     * Returns a project by id
     * @param projectId id of project
     * @return project with given id
     */
    public Project findById(int projectId){
        return projectRepository.findById(projectId).get();
    }


}
