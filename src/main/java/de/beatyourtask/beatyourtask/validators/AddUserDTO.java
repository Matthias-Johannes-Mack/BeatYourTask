package de.beatyourtask.beatyourtask.validators;

import de.beatyourtask.beatyourtask.model.User;

/**
 * Class that represents objects that carry data for add user validation
 */
public class AddUserDTO {

    private User user;
    private int id;


    public AddUserDTO(User user, int projectId) {
        setUser(user);
        setId(projectId);
    }

    // getters and setters
    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
