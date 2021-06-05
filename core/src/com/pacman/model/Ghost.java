package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.pacman.tools.timer.ExampleTimer;

import java.util.ArrayList;
import java.util.Random;

public abstract class Ghost extends Entity {
    public Random random;
    public Texture otherImage;
    public int firstRow, firstCol;
    public boolean canMove = false;
    public Move firstMove;

    {
        random = new Random();
    }

    public Ghost(int row, int col, GameMap gameMap, Move firstMove) {
        super(row, col, gameMap);
        firstRow = row;
        firstCol = col;
        this.firstMove = firstMove;
        moveCondition = firstMove;
    }

    public void resetPosition() {
        shape.x = firstCol * size + gameMap.spaceX;
        shape.y = firstRow * size + gameMap.spaceY;
        myTimer = new ExampleTimer(this,20000L, 5000L);
        canMove = false;
    }

    public ArrayList<Move> capableOfMoves(float delta) {
        ArrayList<Move> probableMoves = new ArrayList<>();

        for (Move move : new Move[]{Move.UP, Move.DOWN, Move.LEFT, Move.RIGHT}) {
            if (moveCondition != move) {
                if (!isCollided(moveCondition, delta))
                    probableMoves.add(move);
            }
        }

        return probableMoves;
    }

    @Override
    public void update(float delta) {
        if (canMove) {
            move(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
