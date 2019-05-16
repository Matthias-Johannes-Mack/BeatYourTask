package de.beatyourtask.beatyourtask.repositories;

import de.beatyourtask.beatyourtask.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Angabe was verwaltet werden soll und Datentyp der ID
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    // Example Query
    List<Project> findByProjectId(Integer id);

}
