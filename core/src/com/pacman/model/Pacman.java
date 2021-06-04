package com.pacman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.security.Key;
import java.text.CharacterIterator;

public class Pacman extends Entity {
    public int life;
    public int eatenGhosts = 0;
    public boolean isCollidedWithWall = false;
    public Move nextMove;

    public Pacman(int row, int col, GameMap gameMap) {
        super(row, col, gameMap);
        create();
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

    public boolean isCollided(Move moveCondition, float delta) {
        TileType tileType;
        switch (moveCondition) {
            case UP:
                tileType = gameMap.getTileTypeByLocation(0, getMiddleXOfShape(), getMiddleYOfShape() + size / 2 + velocity);
                break;
            case DOWN:
                tileType = gameMap.getTileTypeByLocation(0, getMiddleXOfShape(), getMiddleYOfShape() - size / 2 - velocity);
                break;
            case RIGHT:
                tileType = gameMap.getTileTypeByLocation(0, getMiddleXOfShape() + (float) (size / 2.0) + velocity, getMiddleYOfShape());
                break;
            case LEFT:
                tileType = gameMap.getTileTypeByLocation(0, getMiddleXOfShape() - (float) (size / 2.0) - velocity, getMiddleYOfShape());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + moveCondition);
        }
        return tileType.isCollidable();
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
//            if (moveCondition != Move.UP) {
//                if (!isCollided(Move.UP, delta)) {
//                    putAtMiddleOfTile(shape);
//                    moveCondition = Move.UP;
//                }
//            }

            if (moveCondition != Move.UP) {
                if (!isCollided(Move.UP, velocity)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.UP;
                }
            }
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
//            if (moveCondition != Move.DOWN) {
//                if (!isCollided(Move.DOWN, delta)) {
//                    putAtMiddleOfTile(shape);
//                    moveCondition = Move.DOWN;
//                }
//            }

            if (moveCondition != Move.DOWN) {
                if (!isCollided(Move.DOWN, velocity)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.DOWN;
                }
            }
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
//            if (moveCondition != Move.RIGHT) {
//                if (!isCollided(Move.RIGHT, delta)) {
//                    putAtMiddleOfTile(shape);
//                    moveCondition = Move.RIGHT;
//                }
//            }

            if (moveCondition != Move.RIGHT) {
                if (!isCollided(Move.RIGHT, velocity)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.RIGHT;
                }
            }
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            if (moveCondition != Move.LEFT) {
//                if (!isCollided(Move.LEFT, delta)) {
//                    putAtMiddleOfTile(shape);
//                    moveCondition = Move.LEFT;
//                }

                if (!isCollided(Move.LEFT, velocity)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.LEFT;
                }
            }
        }

        move(delta);
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
            switch (moveCondition) {
                case UP:
//                    shape.y += speed * delta;
                    shape.y += velocity;
                    break;
                case DOWN:
//                    shape.y -= speed * delta;
                    shape.y -= velocity;
                    break;
                case RIGHT:
//                    shape.x += speed * delta;
                    shape.x += velocity;
                    break;
                case LEFT:
//                    shape.x -= speed * delta;
                    shape.x -= velocity;
                    break;
            }
        } else {
            putAtMiddleOfTile(shape);
        }
    }
}
