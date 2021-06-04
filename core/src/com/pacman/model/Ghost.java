package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

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

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
