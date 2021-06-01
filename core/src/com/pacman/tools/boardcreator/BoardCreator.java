package com.pacman.tools.boardcreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BoardCreator {

//    public static void main(String[] args) throws Exception {
//        Scanner dataIn = new Scanner(System.in);
//        int tool = dataIn.nextInt();
//        int arz = dataIn.nextInt();
//        int t = dataIn.nextInt();
//
//        for (int i = 0; i < t; i++) {
//            createBoard(arz, tool);
//            System.out.println("");
//        }
//
//        dataIn.close();
//    }

//    public static void createMaze(int tool, int arz) {
////        Scanner dataIn = new Scanner(System.in);
////        int tool = dataIn.nextInt();
////        int arz = dataIn.nextInt();
////        int t = dataIn.nextInt();
//        createBoard(arz, tool);
//
////        dataIn.close();
//    }

    public static int[][] main(String[] args) {
        return createBoard(13, 13);
    }

    /*
     * *****************************************************************************
     * *
     */
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
//        board[0][1] = -1;
//        board[2 * tool][2 * arz - 1] = -1;

//        System.out.println(Arrays.deepToString(board).replace("], ", "\n").replace("[[", "").replace("]]", "").replace("[", "").replace(", ", "").replace("-1", "e").replace("-2", "*").replace("1", "-"));
        System.out.println(Arrays.deepToString(board).replace("], ", "\n").replace("[[", "").replace("]]", "").replace("[", "").replace(", ", "").replace("1", "*").replace("2", " "));
    }

    /*
     * *****************************************************************************
     * *
     */
    // harkat : 0: x++ 1: y-- 2: x-- 3: y++
    public static void move(int xNow, int yNow, int[][] jadval, int n, int m, int[][] divar) {
        jadval[xNow][yNow] = 1;
        int harkat = check(xNow, yNow, jadval, n, m);
        while (harkat != -1) {
            switch (harkat) {
                case 0:
                    divar[2 * xNow + 2][2 * yNow + 1] = 2;
                    move(xNow + 1, yNow, jadval, n, m, divar);
                    break;

                case 1:
                    divar[2 * xNow + 1][2 * yNow] = 2;
                    move(xNow, yNow - 1, jadval, n, m, divar);
                    break;

                case 2:
                    divar[2 * xNow][2 * yNow + 1] = 2;
                    move(xNow - 1, yNow, jadval, n, m, divar);
                    break;

                case 3:
                    divar[2 * xNow + 1][2 * yNow + 2] = 2;
                    move(xNow, yNow + 1, jadval, n, m, divar);
                    break;
            }

            harkat = check(xNow, yNow, jadval, n, m);
        }
    }

    /*
     * *****************************************************************************
     * *
     */
    public static int check(int xNow, int yNow, int[][] jadval, int n, int m) {
        Random random = new Random();
        int randNum = random.nextInt(12);
        ArrayList<Integer> majmooe = new ArrayList<>();
        if ((xNow + 1) < n && jadval[xNow + 1][yNow] == 2)
            majmooe.add(0);

        if ((yNow - 1) >= 0 && jadval[xNow][yNow - 1] == 2)
            majmooe.add(1);

        if ((xNow - 1) >= 0 && jadval[xNow - 1][yNow] == 2)
            majmooe.add(2);

        if ((yNow + 1) < m && jadval[xNow][yNow + 1] == 2)
            majmooe.add(3);

        if (majmooe.size() == 0)
            return -1;
        else {
            randNum = randNum % (majmooe.size());
            return (majmooe.get(randNum));
        }

    }

    public static void reduceWalls(int[][] board, int width, int height) {
        Random random = new Random();
        for (int i = 1; i < 2 * width; i++)
            for (int j = 1; j < 2 * height; j++)
                if (board[i][j] == 1)
                    if (random.nextInt(2) == 0)
                        board[i][j] = 2;
    }
}
