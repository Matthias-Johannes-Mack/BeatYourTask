package de.beatyourtask.beatyourtask.validators;

import de.beatyourtask.beatyourtask.model.User;

public class AddUserDTO {

    private User user;
    private int projectId;


    public AddUserDTO(User user, int projectId) {
        setUser(user);
        setProjectId(projectId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
