package de.beatyourtask.beatyourtask.services;


import de.beatyourtask.beatyourtask.model.Level;
import de.beatyourtask.beatyourtask.repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service that handles the Level | wirefire Summer ´19
 */

@Service
@Transactional
public class LevelService {

    @Autowired
    private LevelRepository LevelRepository;

    /**
     * Saves the Level
     */
    public Level saveLevel(Level Level) {
        return LevelRepository.save(Level);
    }

    /**
     * gets all existing Projects
     * @return List of all project
     */
    public List<Level> getAll() {
        return LevelRepository.findAll();
    }

    /**
     * Returns a project by id
     *
     * @param LevelId id of project
     * @return project with given id
     */
    public Level findLevelById(int LevelId) {
        return LevelRepository.getOne(LevelId);
    }


}
