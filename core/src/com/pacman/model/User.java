package com.pacman.model;

import java.time.LocalDateTime;
import java.util.*;

public class User implements Comparable {
    public final String username;
    private String password;
    public int highScore = 0;
    public LocalDateTime timeOfHighScore;
    public ArrayList<int[][]> mazes;
    public static HashMap<String, User> allUsers;

    {
        mazes = new ArrayList<>();
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

//    public void addMaze() { //it is not empty
//
//    }

    public static ArrayList<User> sortUsersForScoreBoard() {
        Collection<User> demoValues = allUsers.values();

        //Creating an ArrayList of values
        ArrayList<User> users = new ArrayList<>(demoValues);
        Collections.sort(users);
//        System.out.println(users);
        return users;
    }

    public String getPassword() {
        return password;
    }

    public void setHighScore(int score) {
        highScore = score;
        timeOfHighScore = LocalDateTime.now();
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

    public static void setAllUsers(HashMap<String, User> allUsers) {
        if (allUsers != null)
            User.allUsers = allUsers;
        else
            User.allUsers = new HashMap<>();
    }
}
