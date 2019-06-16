package de.beatyourtask.beatyourtask.validators;

import de.beatyourtask.beatyourtask.model.User;

/**
 * Class that represents objects that carry data for add user validation
 */
public class AddUserDTO {

    private User user;
    private int projectId;


    public AddUserDTO(User user, int projectId) {
        setUser(user);
        setProjectId(projectId);
    }

    // getters and setters
    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public int getProjectId() {
        return projectId;
    }

    private void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
