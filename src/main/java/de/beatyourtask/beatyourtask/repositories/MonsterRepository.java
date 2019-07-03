package de.beatyourtask.beatyourtask.repositories;


import de.beatyourtask.beatyourtask.model.Monster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository handling database interaction for the monsters
 */
@Repository
public interface MonsterRepository extends JpaRepository<Monster, Integer> {

}
