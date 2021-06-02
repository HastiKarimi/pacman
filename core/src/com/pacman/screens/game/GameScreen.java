package com.pacman.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pacman.model.GameMap;
import com.pacman.model.User;
import com.pacman.screens.MainClass;
import com.pacman.tools.boardcreator.BoardCreator;

public class GameScreen implements Screen {
    Stage stage;
    MainClass mainClass;
    User user;
    GameMap gameMap;

    public GameScreen(MainClass mainClass, User user) {
        this.user = user;
        this.mainClass = mainClass;
        stage = new Stage(new StretchViewport(512, 512));
        this.gameMap = new GameMap(this, BoardCreator.createMaze());
    }
    @Override
    public void show() {
        stage.addActor(gameMap);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.8f,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
