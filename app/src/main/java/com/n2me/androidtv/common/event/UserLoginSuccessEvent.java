package com.n2me.androidtv.common.event;

import com.n2me.androidtv.common.rest.model.User;
import com.n2me.androidtv.common.rest.model.Users;

public class UserLoginSuccessEvent {

    private Users user;

    public UserLoginSuccessEvent(Users user) {
        this.user = user;
    }

    public Users getUser() {
        return user;
    }
}