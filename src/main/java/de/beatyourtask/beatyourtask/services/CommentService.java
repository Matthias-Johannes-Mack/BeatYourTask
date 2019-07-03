package de.beatyourtask.beatyourtask.services;

import de.beatyourtask.beatyourtask.model.Comment;
import de.beatyourtask.beatyourtask.model.Task;
import de.beatyourtask.beatyourtask.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    public Comment findById(int commentId){
        return commentRepository.findById(commentId).get();
    }

    public List<Comment> findAllByTask(Task task){
        return commentRepository.findByOwnerTask(task);
    }

}
