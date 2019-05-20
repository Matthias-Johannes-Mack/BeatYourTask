package de.beatyourtask.beatyourtask.repositories;

import de.beatyourtask.beatyourtask.model.Tasklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasklistRepository extends JpaRepository<Tasklist, Integer>{
}
