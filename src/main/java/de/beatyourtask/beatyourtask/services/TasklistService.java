package de.beatyourtask.beatyourtask.services;

import de.beatyourtask.beatyourtask.model.Tasklist;
import de.beatyourtask.beatyourtask.repositories.TasklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class TasklistService {

    @Autowired
    private TasklistRepository tasklistRepository;

    public Tasklist saveList(Tasklist tasklist) {
        return tasklistRepository.save(tasklist);
    }
}
