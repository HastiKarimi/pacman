package com.pacman.tools.boardcreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BoardCreator {

    public static int[][] createMaze() {
        return createBoard(13, 13);
    }

    //******************************************************************************
    public static int[][] createBoard(int tool, int arz) {

        int[][] visited = new int[tool][arz];
        int[][] board = new int[2 * tool + 1][2 * arz + 1];

        for (int i = 0; i < (2 * tool + 1); i++)
            Arrays.fill(board[i], 1);

        for (int i = 1; i < 2 * tool; i += 2)
            for (int j = 1; j < 2 * arz; j += 2)
                board[i][j] = 2;

        move(0, 0, visited, tool, arz, board);
        reduceWalls(board, tool, arz);
        putEnergyBombs(board, tool, arz);

        return board;
//        System.out.println(Arrays.deepToString(board).replace("], ", "\n").replace("[[", "").replace("]]", "").replace("[", "").replace(", ", "").replace("1", "*").replace("2", " "));
    }


    //******************************************************************************
    // harkat : 0: x++ 1: y-- 2: x-- 3: y++
    public static void move(int xNow, int yNow, int[][] visitedBoard, int n, int m, int[][] mainBoard) {
        visitedBoard[xNow][yNow] = 1;
        int moveStatus = check(xNow, yNow, visitedBoard, n, m);
        while (moveStatus != -1) {
            switch (moveStatus) {
                case 0:
                    mainBoard[2 * xNow + 2][2 * yNow + 1] = 2;
                    move(xNow + 1, yNow, visitedBoard, n, m, mainBoard);
                    break;

                case 1:
                    mainBoard[2 * xNow + 1][2 * yNow] = 2;
                    move(xNow, yNow - 1, visitedBoard, n, m, mainBoard);
                    break;

                case 2:
                    mainBoard[2 * xNow][2 * yNow + 1] = 2;
                    move(xNow - 1, yNow, visitedBoard, n, m, mainBoard);
                    break;

                case 3:
                    mainBoard[2 * xNow + 1][2 * yNow + 2] = 2;
                    move(xNow, yNow + 1, visitedBoard, n, m, mainBoard);
                    break;
            }

            moveStatus = check(xNow, yNow, visitedBoard, n, m);
        }
    }


    //*****************************************************************************
    public static int check(int xNow, int yNow, int[][] visitedBoard, int n, int m) {
        Random random = new Random();
        ArrayList<Integer> set = new ArrayList<>();
        if ((xNow + 1) < n && visitedBoard[xNow + 1][yNow] == 1)
            set.add(0);

        if ((yNow - 1) >= 0 && visitedBoard[xNow][yNow - 1] == 1)
            set.add(1);

        if ((xNow - 1) >= 0 && visitedBoard[xNow - 1][yNow] == 1)
            set.add(2);

        if ((yNow + 1) < m && visitedBoard[xNow][yNow + 1] == 1)
            set.add(3);

        if (set.size() == 0)
            return -1;
        else {
            int randNum = random.nextInt(12) % (set.size());
            return (set.get(randNum));
        }

    }

    public static void reduceWalls(int[][] board, int width, int height) {
        Random random = new Random();
        for (int i = 1; i < 2 * width; i++) {
            for (int j = 1; j < 2 * height; j++)
                if (board[i][j] == 1)
                    if (random.nextInt(2) != 0)
                        board[i][j] = 2;
        }

        for (int i = 1; i < 2 * width; i += 2) {
            for (int j = 1; j < 2 * height; j += 2) {
                if (board[i - 1][j] == 1 && board[i + 1][j] == 1
                        && board[i][j - 1] == 1 && board[i][j + 1] == 1) {
                    board[i+1][j] = 2;
                    board[i-1][j] = 2;
                }
            }
        }

    }

    public static void putEnergyBombs(int[][] board, int width, int height) {
        board[5][5] = 3;
        board[21][21] = 3;
        board[5][21] = 3;
        board[21][5] = 3;
    }
}
