package com.pacman.controller;

import com.pacman.screens.ChangePasswordMenu;
import com.pacman.screens.Messages;
import com.pacman.model.User;
import com.pacman.tools.Pair;

public class ChangePasswordController { //done
    ChangePasswordMenu changePasswordMenu;
    User user;

    public ChangePasswordController(ChangePasswordMenu changePasswordMenu, User user) {
        this.changePasswordMenu = changePasswordMenu;
        this.user = user;
    }

    public Pair processInfo(String prePass, String newPass, String confirmPass) {
        if (!newPass.equals(confirmPass)) {
            return new Pair(true, Messages.PASSWORDS_DONT_MATCH.getMessages());
        } else if (!user.getPassword().equals(prePass)) {
            return new Pair(true, Messages.INCORRECT_PASS.getMessages());
        }

        user.changePassword(newPass);
        return new Pair(false, Messages.PASS_CHANGED_SUCCESSFULLY.getMessages());
    }
}
