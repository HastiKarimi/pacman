package com.pacman.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pacman.model.*;
import com.pacman.screens.MainClass;
import com.pacman.tools.boardcreator.BoardCreator;

public class GameScreen implements Screen {
    Stage stage;
    MainClass mainClass;
    User user;
    GameMap gameMap;
    public boolean  isGameEnded;

    public GameScreen(MainClass mainClass, User user) {
        this.user = user;
        this.mainClass = mainClass;
        stage = new Stage(new StretchViewport(512, 512));
        this.gameMap = new GameMap(this, BoardCreator.createMaze());
    }


    @Override
    public void show() {
        gameMap.pacman = new Pacman(13, 13, gameMap);
        stage.addActor(gameMap);
//        for (Ghost ghost: gameMap.ghosts)
//            stage.addActor(ghost);
//
        stage.addActor(gameMap.pacman);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.8f,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameMap.update(Gdx.graphics.getDeltaTime());
        stage.act(delta);
        stage.draw();

        if ((isGameEnded = isGameEnded()))
            ;//TODO
    }

    public boolean isGameEnded() {
        int[][] mapOfGame = gameMap.map;
        for (int i = 0; i < 2*13+1; i++) {
            for (int j = 0; j < 2*13+1; j++) {
                if (mapOfGame[i][j]!= TileType.WALL.getId() ||
                        mapOfGame[i][j]!=TileType.NONE.getId())
                    return true;
            }
        }

        return false;
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
