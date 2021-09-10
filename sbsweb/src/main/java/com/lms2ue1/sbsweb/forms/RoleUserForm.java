package com.lms2ue1.sbsweb.forms;

import java.util.List;

import com.lms2ue1.sbsweb.backend.model.User;

public class RoleUserForm {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Boolean empty() {
        return users.isEmpty();
    }
}
