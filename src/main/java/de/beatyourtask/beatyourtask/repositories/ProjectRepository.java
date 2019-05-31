package de.beatyourtask.beatyourtask.repositories;


import de.beatyourtask.beatyourtask.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
