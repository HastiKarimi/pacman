package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Collections;

public class HardGhost extends Ghost {

    public int enterLogicOfHardGhost = 0;

    public HardGhost(int col, int row, GameMap gameMap, Move firstMove, int startRowAnimation, int startColAnimation) {
        super(col, row, gameMap, firstMove, startRowAnimation, startColAnimation);
    }

    @Override
    public void move(float delta) {
        ArrayList<Move> possibleMoves;
        if (isCollided(moveCondition, delta)) {
            putAtMiddleOfTile(shape);
            possibleMoves = nextMove();
            possibleMoves.removeAll(Collections.singleton(moveCondition));

            moveCondition = possibleMoves.get(random.nextInt(possibleMoves.size()));
            doTheMove(delta);
        } else if (getFreeEdges() > 2 && enterLogicOfHardGhost == 0) {
            putAtMiddleOfTile(shape);
            possibleMoves = nextMove();
            moveCondition = possibleMoves.get(random.nextInt(possibleMoves.size()));
            doTheMove(delta);
            enterLogicOfHardGhost--;
        } else {
            doTheMove(delta);
        }

        if (enterLogicOfHardGhost < 0) {
            if (enterLogicOfHardGhost == -12)
                enterLogicOfHardGhost = 0;
            else
                enterLogicOfHardGhost --;
        }
    }

    @Override
    public void setImage(Texture texture) {

    }

    @Override
    public void create() {

    }

    public int getFreeEdges() {
        int col = getColOfTile(shape.x);
        int row = getRowOfTile(shape.y);
        int freeEdges = 0;

        if (isTileAtCoordinateNotWall(col, row + 1))
            freeEdges++;
        if (isTileAtCoordinateNotWall(col, row - 1))
            freeEdges++;
        if (isTileAtCoordinateNotWall(col + 1, row))
            freeEdges++;
        if (isTileAtCoordinateNotWall(col - 1, row))
            freeEdges++;


        return freeEdges;
    }

    public ArrayList<Move> nextMove() {
        int col = getColOfTile(shape.x);
        int row = getRowOfTile(shape.y);

        if (gameMap.pacman.isEnergyMode) {
            return nextMoveEnergy(col, row);
        } else
            return nextMoveNormal(col, row);
    }

    private ArrayList<Move> nextMoveNormal(int col, int row) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        if (isTileAtCoordinateNotWall(col, row + 1)) {
            if (gameMap.pacman.shape.y > shape.y) {
                possibleMoves.add(Move.UP);
                possibleMoves.add(Move.UP);
            }

            possibleMoves.add(Move.UP);
        }

        if (isTileAtCoordinateNotWall(col, row - 1)) {
            if (gameMap.pacman.shape.y < shape.y) {
                possibleMoves.add(Move.DOWN);
                possibleMoves.add(Move.DOWN);
            }

            possibleMoves.add(Move.DOWN);
        }

        if (isTileAtCoordinateNotWall(col + 1, row)) {
            if (gameMap.pacman.shape.x > shape.x) {
                possibleMoves.add(Move.RIGHT);
                possibleMoves.add(Move.RIGHT);
            }

            possibleMoves.add(Move.RIGHT);
        }

        if (isTileAtCoordinateNotWall(col - 1, row)) {
            if (gameMap.pacman.shape.x < shape.x) {
                possibleMoves.add(Move.LEFT);
                possibleMoves.add(Move.LEFT);
            }

            possibleMoves.add(Move.LEFT);
        }

        return possibleMoves;
    }

    private ArrayList<Move> nextMoveEnergy(int col, int row) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        if (isTileAtCoordinateNotWall(col, row + 1)) {
            if (gameMap.pacman.shape.y < shape.y) {
                possibleMoves.add(Move.UP);
                possibleMoves.add(Move.UP);
            }

            possibleMoves.add(Move.UP);
        }

        if (isTileAtCoordinateNotWall(col, row - 1)) {
            if (gameMap.pacman.shape.y > shape.y) {
                possibleMoves.add(Move.DOWN);
                possibleMoves.add(Move.DOWN);
            }

            possibleMoves.add(Move.DOWN);
        }

        if (isTileAtCoordinateNotWall(col + 1, row)) {
            if (gameMap.pacman.shape.x < shape.x) {
                possibleMoves.add(Move.RIGHT);
                possibleMoves.add(Move.RIGHT);
            }

            possibleMoves.add(Move.RIGHT);
        }

        if (isTileAtCoordinateNotWall(col - 1, row)) {
            if (gameMap.pacman.shape.x > shape.x) {
                possibleMoves.add(Move.LEFT);
                possibleMoves.add(Move.LEFT);
            }

            possibleMoves.add(Move.LEFT);
        }

        return possibleMoves;
    }

    public boolean isTileAtCoordinateNotWall(int col, int row) {
        return gameMap.getTileTypeByCoordinate(0, col, row) != TileType.WALL;
    }
}
