package de.beatyourtask.beatyourtask.repositories;


import de.beatyourtask.beatyourtask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository handling database interaction for the users
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User findByEmail(String email);
}
