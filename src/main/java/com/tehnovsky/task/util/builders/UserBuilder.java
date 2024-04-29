package com.tehnovsky.task.util.builders;

import com.tehnovsky.task.model.User;

public class UserBuilder {
    private UserBuilder() {

    }

    public static User buildUser(long id, String name) {
        return User.builder()
                .id(id)
                .username(name)
                .build();
    }
}
