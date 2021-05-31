package com.pacman.controller;

import com.pacman.menues.ChangePasswordMenu;
import com.pacman.menues.Messages;
import com.pacman.menues.SignUpMenu;
import com.pacman.model.User;
import jdk.internal.net.http.common.Pair;

public class ChangePasswordController { //done
    ChangePasswordMenu changePasswordMenu;
    User user;

    public ChangePasswordController(ChangePasswordMenu changePasswordMenu, User user) {
        this.changePasswordMenu = changePasswordMenu;
    }

    public Pair<Boolean, String> processInfo(String prePass, String newPass, String confirmPass) {
        if (!prePass.equals(confirmPass)) {
//            changePasswordMenu.showMessage(Messages.PASSWORDS_DONT_MATCH.getMessages(), true);
            return new Pair<>(true, Messages.PASSWORDS_DONT_MATCH.getMessages());
        } else if (!user.getPassword().equals(newPass)) {
//            changePasswordMenu.showMessage(Messages.INCORRECT_PASS.getMessages(), true);
            return new Pair<>(true, Messages.INCORRECT_PASS.getMessages());
        }

//        changePasswordMenu.showMessage(Messages.PASS_CHANGED_SUCCESSFULLY.getMessages(), false);
        user.changePassword(newPass);
        return new Pair<>(false, Messages.PASS_CHANGED_SUCCESSFULLY.getMessages());
    }
}
