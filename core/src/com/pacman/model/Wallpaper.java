package com.pacman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Wallpaper extends Actor {
    Texture picture;

    {
        picture = new Texture(Gdx.files.internal("wallpapers/wallpaper1.jpg"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(picture, 0, 0, 1024, 2034);
    }
}
