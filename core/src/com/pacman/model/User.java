package com.pacman.model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    public int highScore;
    public LocalDateTime timeOfHighScore;
    public static HashMap<String, User> allUsers;

    static {
        allUsers = new HashMap<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        allUsers.put(username, this);
    }

    public static User findUser(String username) {
        return allUsers.getOrDefault(username, null);
    }

    public void changePassword(String newPas) {

    }

    public void deleteAccount() {

    }

    public void addMaze() { //it is not empty

    }

    public String getPassword() {
        return password;
    }
}
