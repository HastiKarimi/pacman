package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.tools.timer.ExampleTimer;

public abstract class Entity extends Actor {
    public Rectangle shape;
    public Texture nowImage;
    public int speed = 60;
    public boolean isEnergyMode;
    public int size;
    public GameMap gameMap;
    public ExampleTimer myTimer;
    public Move moveCondition;

    {
        isEnergyMode = false;
    }

    public Entity(int col, int row, GameMap gameMap) {
        this.gameMap = gameMap;
        size = TileType.TILE_SIZE;
        shape = new Rectangle();
        shape.x = col * size + gameMap.spaceX;
        shape.y = row * size + gameMap.spaceY;
        shape.width = size;
        shape.height = size;
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public abstract void timerEnd();

    public void setSizeOfEntity(int sizeOfEntity){
        this.size = sizeOfEntity;
    };

    public abstract void update(float delta);

    public abstract void setImage(Texture texture);

    public abstract void create();

    public void putAtMiddleOfTile(Rectangle rectangle) {
        int newX = ((int) (rectangle.x - gameMap.spaceX + size / 2)) / TileType.TILE_SIZE;
        int newY = ((int) (rectangle.y - gameMap.spaceY + size / 2)) / TileType.TILE_SIZE;

        rectangle.x = (float) (newX * TileType.TILE_SIZE + gameMap.spaceX);
        rectangle.y = (float) (newY * TileType.TILE_SIZE + gameMap.spaceY);
    }

    public float getMiddleXOfShape() {
        return shape.x + shape.width / 2;
    }

    public float getMiddleYOfShape() {
        return shape.y + shape.height / 2;
    }

    public abstract void move(float delta);

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
}
