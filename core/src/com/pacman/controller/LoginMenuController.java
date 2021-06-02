package com.pacman.controller;

import com.pacman.screens.LoginMenu;
import com.pacman.screens.Messages;
import com.pacman.model.User;
import com.pacman.tools.Pair;

public class LoginMenuController {
    LoginMenu loginMenu;
    User user;

    public LoginMenuController(LoginMenu loginMenu) {
        this.loginMenu = loginMenu;
    }

    public Pair processInfo(String username, String password) {
        if ((user = User.findUser(username)) == null) {
//            loginMenu.showMessage(Messages.USER_DOESNT_EXIST.getMessages(), true);
            return new Pair(true, Messages.USER_DOESNT_EXIST.getMessages());
        } else if (!user.getPassword().equals(password)) {
//            loginMenu.showMessage(Messages.INCORRECT_PASS.getMessages(), true);
            return new Pair(true, Messages.INCORRECT_PASS.getMessages());
        }

//        loginMenu.showMessage(Messages.LOGIN_SUCCESSFUL.getMessages(), false);
        return new Pair(false, Messages.LOGIN_SUCCESSFUL.getMessages());
    }

    public User getUser() {
        return user;
    }
}
