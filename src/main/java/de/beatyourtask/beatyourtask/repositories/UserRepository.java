package de.beatyourtask.beatyourtask.repositories;


import de.beatyourtask.beatyourtask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);

}
