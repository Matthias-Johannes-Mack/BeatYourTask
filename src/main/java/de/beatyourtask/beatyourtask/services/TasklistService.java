package de.beatyourtask.beatyourtask.services;

import de.beatyourtask.beatyourtask.model.Tasklist;
import de.beatyourtask.beatyourtask.repositories.TasklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * Class for handeling Tasklist in the database
 */
@Service
public class TasklistService {

    @Autowired
    private TasklistRepository tasklistRepository;

    /**
     * Saves a tasklist in the database
     * @param tasklist tasklist to be saved
     * @return
     */
    public Tasklist saveList(Tasklist tasklist) {
        return tasklistRepository.save(tasklist);
    }

    /**
     * get all tasklist from the database
     * @return all tasklists
     */
    @Transactional
    public List < Tasklist > getAllTaskLists() {
        return tasklistRepository.findAll();
    }

    /**
     * delets a tasklist
     * @param id of the tasklist to be deleted
     */
    @Transactional
    public void  deleteTasklistById(int id) {
        tasklistRepository.deleteById(id);
    }

    public Tasklist loadTasklistById(int id) { return tasklistRepository.getOne(id); }
}
