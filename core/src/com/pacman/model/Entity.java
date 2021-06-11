package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;

public abstract class Entity extends Actor {
    public Rectangle shape;
    public Texture nowImage;
    public int speed;
    public int size;
    public GameMap gameMap;
    public Move moveCondition;
    public HashMap<Move, TextureRegion[]> myFrames;
    public long startTime;
    public float elapsedTime;
    public float targetDurationInSec;
    public int firstRow, firstCol;
    public Animation<TextureRegion> walkAnimation;

    {
        size = TileType.TILE_SIZE;
    }

    public Entity(int col, int row, GameMap gameMap) {
        this.gameMap = gameMap;
        shape = new Rectangle();
        setShape(col, row);
        this.firstCol = col;
        this.firstRow = row;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    //abstracts
    public abstract void timerEnd();

    public abstract void update(float delta);

    public abstract void setImage(Texture texture);

    public abstract void create();

    public abstract void resetPosition(boolean isEaten);

    public abstract void move(float delta);

    //setters
    public void setSpeed(int amount) {
        speed = amount;
    }

    public void setTimer(float targetDurationInSec) {
        this.targetDurationInSec = targetDurationInSec;
        startTime = System.currentTimeMillis();
    }

    public void setShape(int col, int row) {
        shape.x = col * size + gameMap.spaceX;
        shape.y = row * size + gameMap.spaceY;
        shape.width = size;
        shape.height = size;
    }

    //shared methods
    public float getMiddleXOfShape() {
        return shape.x + shape.width / 2;
    }

    public float getMiddleYOfShape() {
        return shape.y + shape.height / 2;
    }

    public void putAtMiddleOfTile(Rectangle rectangle) {
        int newX = getColOfTile(shape.x);
        int newY = getRowOfTile(shape.y);

        rectangle.x = (float) (newX * TileType.TILE_SIZE + gameMap.spaceX);
        rectangle.y = (float) (newY * TileType.TILE_SIZE + gameMap.spaceY);
    }

    public int getColOfTile(float x) {
        return ((int) (x - gameMap.spaceX + size / 2)) / TileType.TILE_SIZE;
    }

    public int getRowOfTile(float y) {
        return ((int) (y - gameMap.spaceY + size / 2)) / TileType.TILE_SIZE;
    }

    public boolean isCollided(Move moveCondition, float delta) {
        TileType tileType;
        switch (moveCondition) {
            case UP:
                tileType = gameMap.getTileTypeByLocation(0, getMiddleXOfShape(), getMiddleYOfShape() + (float) (size / 2.0) + speed * delta);
                break;
            case DOWN:
                tileType = gameMap.getTileTypeByLocation(0, getMiddleXOfShape(), getMiddleYOfShape() - (float) (size / 2.0) - speed * delta);
                break;
            case RIGHT:
                tileType = gameMap.getTileTypeByLocation(0, getMiddleXOfShape() + (float) (size / 2.0) + speed * delta, getMiddleYOfShape());
                break;
            case LEFT:
                tileType = gameMap.getTileTypeByLocation(0, getMiddleXOfShape() - (float) (size / 2.0) - speed * delta, getMiddleYOfShape());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + moveCondition);
        }

        return tileType.isCollidable();
    }

    public void doTheMove(float delta) {
        switch (moveCondition) {
            case UP:
                shape.y += speed * delta;
                break;
            case DOWN:
                shape.y -= speed * delta;
                break;
            case RIGHT:
                shape.x += speed * delta;
                break;
            case LEFT:
                shape.x -= speed * delta;
                break;
        }
    }

    public boolean isTimerEnded() {
        elapsedTime = (float) ((System.currentTimeMillis() - startTime) / 1000.0);
        if (elapsedTime >= targetDurationInSec) {
            timerEnd();
            return true;
        }
        return false;
    }
}
