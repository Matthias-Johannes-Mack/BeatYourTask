package de.beatyourtask.beatyourtask.validators;

import de.beatyourtask.beatyourtask.model.Comment;

public class CommentDTO {

    private Integer commentId;
    private String message;
    private Integer taskId;

    public CommentDTO() {

    }


    public CommentDTO convert(Comment comment){
        this.commentId = comment.getId();
        this.message = comment.getMessage().replace("<br />","\n");
        this.taskId = comment.getOwnerTask().getTaskId();

        return this;
    }

    // getters and setters
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}

