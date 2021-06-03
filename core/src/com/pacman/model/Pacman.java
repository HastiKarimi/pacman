package com.pacman.model;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Pacman extends Entity {
    public int life;
    public int eatenGhosts = 0;

    public Pacman(int row, int col, GameMap gameMap) {
        super(row, col, gameMap);
    }

    public int eatGhost(Ghost ghost) {
        eatenGhosts++;
        ghost.resetPosition();
        return 200 * eatenGhosts;
    }

    public int eatNormalFood(int row, int col) {
        gameMap.map[row][col] = TileType.NONE.getId();
        return 5;
    }

    public int eatEnergyBomb(int row, int col) {
        gameMap.map[row][col] = TileType.NONE.getId();
        return 0;
    }

    public void disableEnergyBomb() {
        eatenGhosts = 0;
    }

    public boolean isCollided() {
        return true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        batch.draw();
    }

    @Override
    public void timerEnd() {

    }

    @Override
    public void setSizeOfEntity(int sizeOfEntity) {

    }

    @Override
    public void update() {

    }
}
