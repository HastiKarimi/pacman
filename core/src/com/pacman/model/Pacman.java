package com.pacman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Pacman extends Entity {
    public int life;
    public int eatenGhosts = 0;
    public boolean isCollidedWithWall = false;
    public Move nextMove;
    public int score = 0;

    public Pacman(int row, int col, GameMap gameMap) {
        super(row, col, gameMap);
        create();
    }

    public void eatGhost(Ghost ghost) {
        eatenGhosts++;
        ghost.resetPosition();
        score += 200 * eatenGhosts;
    }

    public void eatNormalFood() {
        score += 5;
    }

    public void eatEnergyBomb() {
        score += 0;
    }

    public void disableEnergyBomb() {
        eatenGhosts = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(nowImage, shape.x, shape.y);
    }

    @Override
    public void timerEnd() {

    }

    @Override
    public void setSizeOfEntity(int sizeOfEntity) {

    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            if (moveCondition != Move.UP) {
                if (!isCollided(Move.UP, delta)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.UP;
                }
            }
        }


        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            if (moveCondition != Move.DOWN) {
                if (!isCollided(Move.DOWN, delta)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.DOWN;
                }
            }
        }


        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            if (moveCondition != Move.RIGHT) {
                if (!isCollided(Move.RIGHT, delta)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.RIGHT;
                }
            }
        }


        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            if (moveCondition != Move.LEFT) {
                if (!isCollided(Move.LEFT, delta)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.LEFT;
                }
            }
        }


        move(delta);
        switch (gameMap.getTileTypeByLocation(0, getMiddleXOfShape(), getMiddleYOfShape())) {
            case FOOD:
                gameMap.eatTileTypeContent(0, getMiddleXOfShape(), getMiddleYOfShape());
                this.eatNormalFood();
                break;
            case ENERGY_BOMB:
                gameMap.eatTileTypeContent(0, getMiddleXOfShape(), getMiddleYOfShape());
                this.eatEnergyBomb();
                break;
            case NONE:
                break;
        }
    }

    @Override
    public void setImage(Texture texture) {

    }

    @Override
    public void create() {
        moveCondition = Move.UP;
        nowImage = new Texture(Gdx.files.internal("pacman.png"));
        setImage(nowImage);
    }

    public void move(float delta) {
        if (!isCollided(moveCondition, delta)) {
            doTheMove(delta);
        } else {
            putAtMiddleOfTile(shape);
        }
    }
}
