package com.pacman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.tools.timer.ExampleTimer;

public abstract class Entity extends Actor {
    public Rectangle shape;
    public Texture nowImage;
    public Vector2 pos;
    public int speed;
    public boolean isEnergyMode;
    public int size;
    public GameMap gameMap;
    public ExampleTimer myTimer;

    {
        isEnergyMode = false;
    }

    public Entity(int row, int col, GameMap gameMap) {
        this.gameMap = gameMap;
        pos = new Vector2(row * size + gameMap.spaceX, col * size + gameMap.spaceY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public abstract void timerEnd();

    public abstract void setSizeOfEntity(int sizeOfEntity);

    public abstract void update();
}
