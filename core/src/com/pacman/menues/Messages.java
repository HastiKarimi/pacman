package com.pacman.menues;

public enum Messages {
    USER_EXIST("this user already exists"),
    USER_DOESNT_EXIST("this user does not exists"),
    PASSWORDS_DONT_MATCH("the passwords doesn't match"),
    REGISTER_SUCCESSFUL("you have registered successfully"),
    LOGIN_SUCCESSFUL("you have successfully logged in"),
    INCORRECT_PASS("you have entered an incorrect password"),
    PASS_CHANGED_SUCCESSFULLY("your password was changed successfully");


    String messages;
    Messages(String message) {
        this.messages = message;
    }

    public String getMessages() {
        return messages;
    }
}
