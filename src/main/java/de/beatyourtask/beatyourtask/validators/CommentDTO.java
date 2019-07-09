package de.beatyourtask.beatyourtask.validators;

import de.beatyourtask.beatyourtask.model.Comment;

/**
 * Class that represents objects that carry data for editing a comment
 */
public class CommentDTO {

    private Integer commentId;
    private String message;
    private Integer taskId;

    public CommentDTO() {

    }

    /**
     * Converts a comment into a commentDTO object by converting id, taskId and message
     * @param comment the comment that needes to be converted
     * @return commentDTO object containing converted data
     */
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

