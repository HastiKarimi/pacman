package com.pacman.controller;

import com.pacman.screens.Messages;
import com.pacman.screens.SignUpMenu;
import com.pacman.model.User;
import com.pacman.tools.Pair;

public class SignUpMenuController {
    SignUpMenu signUpMenu;

    public SignUpMenuController(SignUpMenu signUpMenu) {
        this.signUpMenu = signUpMenu;
    }

    public Pair processInfo(String username, String password, String confirmPassword) {
        if (User.findUser(username) != null) {
            return new Pair(true, Messages.USER_EXIST.getMessages());
        } else if (!password.equals(confirmPassword)) {
            return new Pair(true, Messages.PASSWORDS_DONT_MATCH.getMessages());
        }


        new User(username, password);
        return new Pair(false, Messages.REGISTER_SUCCESSFUL.getMessages());
    }
}
