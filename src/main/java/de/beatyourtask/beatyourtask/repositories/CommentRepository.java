package de.beatyourtask.beatyourtask.repositories;

import de.beatyourtask.beatyourtask.model.Comment;
import de.beatyourtask.beatyourtask.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

   List<Comment> findByOwnerTask(Task task);
}
