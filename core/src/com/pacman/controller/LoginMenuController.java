package com.pacman.controller;

import com.pacman.menues.LoginMenu;
import com.pacman.menues.Messages;
import com.pacman.menues.SignUpMenu;
import com.pacman.model.User;
import jdk.internal.net.http.common.Pair;
import org.graalvm.compiler.lir.LIRInstruction;

public class LoginMenuController {
    LoginMenu loginMenu;
    User user;

    public LoginMenuController(LoginMenu loginMenu) {
        this.loginMenu = loginMenu;
    }

    public Pair<Boolean, String> processInfo(String username, String password) {
        if ((user = User.findUser(username)) == null) {
//            loginMenu.showMessage(Messages.USER_DOESNT_EXIST.getMessages(), true);
            return new Pair<>(true, Messages.USER_DOESNT_EXIST.getMessages());
        } else if (!user.getPassword().equals(password)) {
//            loginMenu.showMessage(Messages.INCORRECT_PASS.getMessages(), true);
            return new Pair<>(true, Messages.INCORRECT_PASS.getMessages());
        }

//        loginMenu.showMessage(Messages.LOGIN_SUCCESSFUL.getMessages(), false);
        return new Pair<>(false, Messages.LOGIN_SUCCESSFUL.getMessages());
    }

    public User getUser() {
        return user;
    }
}
