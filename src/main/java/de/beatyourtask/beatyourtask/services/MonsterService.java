package de.beatyourtask.beatyourtask.services;


import de.beatyourtask.beatyourtask.model.Monster;
import de.beatyourtask.beatyourtask.repositories.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service that handles the monster | wirefire Summer ´19
 */

@Service
@Transactional
public class MonsterService {

    @Autowired
    private MonsterRepository monsterRepository;

    /**
     * Saves the monster
     */
    public Monster saveMonster(Monster monster) {
        return monsterRepository.save(monster);
    }

    /**
     * gets all existing Projects
     * @return List of all project
     */
    public List<Monster> getAll() {
        return monsterRepository.findAll();
    }

    /**
     * Returns a project by id
     *
     * @param monsterId id of project
     * @return project with given id
     */
    public Monster findMonsterById(int monsterId) {
        return monsterRepository.getOne(monsterId);
    }


}
