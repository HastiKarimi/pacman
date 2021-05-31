package com.pacman.controller;

import com.pacman.menues.Messages;
import com.pacman.menues.SignUpMenu;
import com.pacman.model.User;
import jdk.internal.net.http.common.Pair;

public class SignUpMenuController {
    SignUpMenu signUpMenu;

    public SignUpMenuController(SignUpMenu signUpMenu) {
        this.signUpMenu = signUpMenu;
    }

    public Pair<Boolean, String> processInfo(String username, String password, String confirmPassword) {
        if (User.findUser(username) != null) {
//            signUpMenu.showMessage(Messages.USER_EXIST.getMessages(), true);
            return new Pair<>(true, Messages.USER_DOESNT_EXIST.getMessages());
        } else if (!password.equals(confirmPassword)) {
//            signUpMenu.showMessage(Messages.PASSWORDS_DONT_MATCH.getMessages(), true);
            return new Pair<>(true, Messages.USER_DOESNT_EXIST.getMessages());
        }

//        signUpMenu.showMessage(Messages.REGISTER_SUCCESSFUL.getMessages(), false);
        new User(username, password);   //TODO
        return new Pair<>(false, Messages.REGISTER_SUCCESSFUL.getMessages());
    }
}
