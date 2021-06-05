package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class EasyGhost extends Ghost{

    public EasyGhost(int row, int col, GameMap gameMap, Move firstMove) {
        super(row, col, gameMap, firstMove);
    }

    @Override
    public void timerEnd() {
        canMove = true;
        moveCondition = firstMove;
    }

    @Override
    public void move(float delta) {
        if (!isCollided(moveCondition, delta)) {
            doTheMove(delta);
        } else {
            putAtMiddleOfTile(shape);
            ArrayList<Move> capableOfMoves = capableOfMoves(delta);
            moveCondition = capableOfMoves.get(random.nextInt(capableOfMoves.size()));
        }
    }

    @Override
    public void setImage(Texture texture) {

    }

    @Override
    public void create() {

    }
}
