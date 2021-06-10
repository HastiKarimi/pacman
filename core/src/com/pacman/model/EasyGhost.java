package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class EasyGhost extends Ghost{

    public EasyGhost(int col, int row, GameMap gameMap, Move firstMove, int startRowAnimation, int startColAnimation) {
        super(col, row, gameMap, firstMove, startRowAnimation ,startColAnimation );
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
