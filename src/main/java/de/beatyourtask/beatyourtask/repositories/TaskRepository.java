package de.beatyourtask.beatyourtask.repositories;

import de.beatyourtask.beatyourtask.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    
}