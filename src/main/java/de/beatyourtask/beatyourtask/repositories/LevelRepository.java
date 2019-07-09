package de.beatyourtask.beatyourtask.repositories;


import de.beatyourtask.beatyourtask.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository handling database interaction for the monsters
 */
@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {

}
