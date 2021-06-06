package com.pacman.tools.boardcreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BoardCreator {

    public static int[][] createMaze() {
        return createBoard(13, 13);
    }

//    public static void main(String[] args) {
//        createBoard(13, 13);
//    }

    //******************************************************************************
    public static int[][] createBoard(int width, int height) {

        int[][] board = new int[2 * width + 1][2 * height + 1];

        for (int i = 0; i < (2 * width + 1); i++)
            Arrays.fill(board[i], 1);

        for (int i = 1; i < 2 * width; i += 2)
            for (int j = 1; j < 2 * height; j += 2)
                board[i][j] = 2;


        create(board, width, height);
//        reduceWalls(board, tool, arz);
        putEnergyBombs(board, width, height);


//        System.out.println(Arrays.deepToString(board).replace("], ", "\n").replace("[[", "").replace("]]", "").replace("[", "").replace(", ", "").replace("2", "0"));
        return board;
    }


    //******************************************************************************
    // harkat : 0: x++ 1: y-- 2: x-- 3: y++

    public static void create(int[][] board, int width, int height) {
        int moveStatus;
        for (int i = 1; i < 2 * width + 1; i += 2) {
            for (int j = 1; j < 2 * height + 1; j += 2) {
                do {
                    moveStatus = addRandomEdge(board, i, j, width, height);
                    if (moveStatus != -1)
                        switch (moveStatus) {
                            case 0:
                                board[i + 1][j] = 2;
                                break;
                            case 1:
                                board[i][j - 1] = 2;
                                break;
                            case 2:
                                board[i - 1][j] = 2;
                                break;
                            case 3:
                                board[i][j + 1] = 2;
                                break;
                        }
                } while (countEdges(board, i, j, width, height) < 2);
            }
        }
    }


    //*****************************************************************************

    public static void reduceWalls(int[][] board, int width, int height) {
        Random random = new Random();
        for (int i = 1; i < 2 * width; i++) {
            for (int j = 1; j < 2 * height; j++)
                if (board[i][j] == 1)
                    if (random.nextInt(3) == 0)
                        board[i][j] = 2;
        }

    }

    public static void putEnergyBombs(int[][] board, int width, int height) {
        board[5][5] = 3;
        board[21][21] = 3;
        board[5][21] = 3;
        board[21][5] = 3;
        board[14][14] = 4;
    }

    public static int countEdges(int[][] board, int xNow, int yNow, int width, int height) {
        int count = 0;
        if ((xNow + 1) < 2 * width + 1 && board[xNow + 1][yNow] == 2) count++;
        if ((yNow - 1) >= 0 && board[xNow][yNow - 1] == 2) count++;
        if ((xNow - 1) >= 0 && board[xNow - 1][yNow] == 2) count++;
        if ((yNow + 1) < 2 * height + 1 && board[xNow][yNow + 1] == 2) count++;

        return count;
    }

    public static int addRandomEdge(int[][] board, int xNow, int yNow, int width, int height) {
        Random random = new Random();
        ArrayList<Integer> set = new ArrayList<>();
        if ((xNow + 1) < 2 * width && board[xNow + 1][yNow] == 1)
            set.add(0);

        if ((yNow - 1) > 0 && board[xNow][yNow - 1] == 1)
            set.add(1);

        if ((xNow - 1) > 0 && board[xNow - 1][yNow] == 1)
            set.add(2);

        if ((yNow + 1) < 2 * height && board[xNow][yNow + 1] == 1)
            set.add(3);

        if (set.size() == 0)
            return -1;
        else {
            int randNum = random.nextInt(12) % (set.size());
            return (set.get(randNum));
        }
    }
}
