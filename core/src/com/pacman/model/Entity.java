package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.tools.timer.ExampleTimer;

public abstract class Entity extends Actor {
    public Rectangle shape;
    public Texture nowImage;
    public Vector2 pos;
    public int speed = 20;
    public float velocity = (float) (1 / 3.0);
    public boolean isEnergyMode;
    public int size;
    public GameMap gameMap;
    public ExampleTimer myTimer;
    public Move moveCondition;

    {
        isEnergyMode = false;
    }

    public Entity(int row, int col, GameMap gameMap) {
        this.gameMap = gameMap;
//        pos = new Vector2(row * size + gameMap.spaceX, col * size + gameMap.spaceY);
        size = TileType.TILE_SIZE;
        shape = new Rectangle();
        shape.x = row * size + gameMap.spaceX;
        shape.y = col * size + gameMap.spaceY;
        shape.width = size;
        shape.height = size;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public abstract void timerEnd();

    public abstract void setSizeOfEntity(int sizeOfEntity);

    public abstract void update(float delta);

    public abstract void setImage(Texture texture);

    public abstract void create();

    public void putAtMiddleOfTile(Rectangle rectangle) {
        int newX = ((int) (rectangle.x - gameMap.spaceX + size / 2)) / TileType.TILE_SIZE;
        int newY = ((int) (rectangle.y - gameMap.spaceY + size / 2)) / TileType.TILE_SIZE;

//        newX -= gameMap.spaceX;
//        newY -= gameMap.spaceY;
//
//        newX = newX / TileType.TILE_SIZE;
//        newY = newY / TileType.TILE_SIZE;
//
//        newX *= TileType.TILE_SIZE;
//        newY *= TileType.TILE_SIZE;
//        newX += gameMap.spaceX;
//        newY += gameMap.spaceY;

        rectangle.x = (float) (newX * TileType.TILE_SIZE + gameMap.spaceX);
        rectangle.y = (float) (newY * TileType.TILE_SIZE + gameMap.spaceY);
    }

    public float getMiddleXOfShape() {
        return shape.x + shape.width / 2;
    }

    public float getMiddleYOfShape() {
        return shape.y + shape.height / 2;
    }
}
