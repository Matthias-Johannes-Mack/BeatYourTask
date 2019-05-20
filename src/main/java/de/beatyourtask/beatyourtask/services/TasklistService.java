package de.beatyourtask.beatyourtask.services;

import de.beatyourtask.beatyourtask.model.Tasklist;
import de.beatyourtask.beatyourtask.repositories.TasklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;



@Service
public class TasklistService {

    @Autowired
    private TasklistRepository tasklistRepository;

    public Tasklist saveList(Tasklist tasklist) {
        return tasklistRepository.save(tasklist);
    }

    @Transactional
    public List < Tasklist > getAllTaskLists() {
        return tasklistRepository.findAll();
    }

    @Transactional
    public void  deleteTasklistById(int id) {
        tasklistRepository.deleteById(id);
    }


}
