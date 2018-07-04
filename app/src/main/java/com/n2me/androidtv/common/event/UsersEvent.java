package com.n2me.androidtv.common.event;

import com.n2me.androidtv.common.rest.model.Users;

public class UsersEvent {
    private Users mUsers;

    public UsersEvent(Users users) {
        mUsers = users;
    }

    public Users getUsers() {
        return mUsers;
    }
}