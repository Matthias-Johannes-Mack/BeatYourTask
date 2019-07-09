package de.beatyourtask.beatyourtask.repositories;


import de.beatyourtask.beatyourtask.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository handling database interaction for the projects
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
