package com.pacman.model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class User implements Comparable {
    private String username;
    private String password;
    public int highScore = 0;
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
        password = newPas;
    }

    public void deleteAccount() {
        allUsers.remove(username);
    }

    public void addMaze() { //it is not empty

    }

    public String getPassword() {
        return password;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null)
            return 1;
        else if (!(o instanceof User)) {
            return 1;
        }
        else{
            User second = (User) o;
            if (this.highScore > second.highScore)
                return -1;
            else if (this.highScore < second.highScore)
                return 1;
            else{
                if (timeOfHighScore == null)
                    return 0;
                else if (this.timeOfHighScore.isBefore(second.timeOfHighScore))
                    return -1;
                else
                    return 1;
            }
        }
    }
}
