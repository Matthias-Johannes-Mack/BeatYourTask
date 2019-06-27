package de.beatyourtask.beatyourtask.services;

import de.beatyourtask.beatyourtask.model.Tasklist;
import de.beatyourtask.beatyourtask.repositories.TaskRepository;
import de.beatyourtask.beatyourtask.repositories.TasklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.beatyourtask.beatyourtask.model.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("taskService")
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task getTaskById(int id) { return taskRepository.getOne(id); }
}
