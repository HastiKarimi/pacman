package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Ghost extends Entity {
    public Texture otherImage;
    public int firstRow, firstCol;

    public Ghost(int row, int col, GameMap gameMap) {
        super(row, col, gameMap);
        firstRow = row;
        firstCol = col;
    }

    public void resetPosition() {

    }
}
