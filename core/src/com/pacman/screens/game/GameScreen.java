package com.pacman.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pacman.model.*;
import com.pacman.screens.MainClass;
import com.pacman.screens.ScreenType;
import com.pacman.tools.boardcreator.BoardCreator;

import java.awt.*;
import java.util.HashMap;

public class GameScreen implements Screen {
    Stage stage;
    MainClass mainClass;
    User user;
    GameMap gameMap;
    public boolean isGameEnded;
    public TextureRegion tmp[][];
    public float stateTime = 0f;
    public Label scoreLabel, lifeLabel;
    public ScreenType waitingScreen;

    public GameScreen(MainClass mainClass, User user, HashMap<String, Object> data) {
        assembleAnimations();
        this.user = user;
        this.mainClass = mainClass;
        stage = new Stage(new StretchViewport(512, 512));
        extractData(data);
    }


    @Override
    public void show() {
        stage.addActor(gameMap);
        for (Ghost ghost : gameMap.ghosts)
            stage.addActor(ghost);

        stage.addActor(gameMap.pacman);
        scoreLabel = mainClass.createLabel("score: " + gameMap.pacman.score);
        scoreLabel.setX(230f);
        scoreLabel.setY(425f);
        scoreLabel.setFontScale(0.5f);

        lifeLabel = mainClass.createLabel("life: " + gameMap.pacman.life);
        lifeLabel.setX(120f);
        lifeLabel.setY(425f);
        lifeLabel.setFontScale(0.5f);

        stage.addActor(lifeLabel);
        stage.addActor(scoreLabel);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.8f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += delta;

        gameMap.update(Gdx.graphics.getDeltaTime());
        scoreLabel.setText("score: " + gameMap.pacman.score);
        lifeLabel.setText("life: " + gameMap.pacman.life);
        stage.act(delta);
        stage.draw();

        if ((isGameEnded = isGameEnded()))
            fillMap();

        if (MainClass.isSuccessful && waitingScreen == ScreenType.USER) {
            if (user != null) {
                checkHighScore();
            }
            mainClass.setScreenToUserMenu(user);
        }
        if (MainClass.isSuccessful && waitingScreen == null) {
            MainClass.isSuccessful = false;
        }

    }

    public void checkHighScore() {
        if (user.highScore < gameMap.pacman.score)
            user.setHighScore(gameMap.pacman.score);
    }

    public boolean isGameEnded() {
        int[][] mapOfGame = gameMap.map;
        for (int i = 0; i < 2 * 13 + 1; i++) {
            for (int j = 0; j < 2 * 13 + 1; j++) {
                if (mapOfGame[i][j] == TileType.FOOD.getId() ||
                        mapOfGame[i][j] == TileType.ENERGY_BOMB.getId())
                    return false;
            }
        }
        return true;
    }

    public void fillMap() {
        for (int i = 0; i < 2 * 13 + 1; i++)
            for (int j = 0; j < 2 * 13 + 1; j++)
                if (gameMap.map[i][j] != TileType.WALL.getId())
                    gameMap.map[i][j] = TileType.FOOD.getId();

        gameMap.map[5][5] = 3;
        gameMap.map[21][21] = 3;
        gameMap.map[5][21] = 3;
        gameMap.map[21][5] = 3;
        gameMap.pacman.life += 2;
        gameMap.pacman.isEnergyMode = false;
        gameMap.pacman.overlapWithGhost(null);
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

    public void assembleAnimations() {  //y:0 to 3 - 4 to 7      x:0 to 5 - 6 to 11
        Texture ghostsSheet = new Texture(Gdx.files.internal("ghost.png"));
        tmp = TextureRegion.split(ghostsSheet, TileType.TILE_SIZE, TileType.TILE_SIZE);
    }

    private void createEasyGhost() {
        gameMap.ghosts[0] = new EasyGhost(1, 1, gameMap, Move.UP, 0, 0);
        gameMap.ghosts[1] = new EasyGhost(25, 1, gameMap, Move.LEFT, 0, 6);
        gameMap.ghosts[2] = new EasyGhost(1, 25, gameMap, Move.RIGHT, 4, 0);
        gameMap.ghosts[3] = new EasyGhost(25, 25, gameMap, Move.DOWN, 4, 6);
    }

    private void createHardGhost() {
        gameMap.ghosts[0] = new HardGhost(1, 1, gameMap, Move.UP, 0, 0);
        gameMap.ghosts[1] = new HardGhost(25, 1, gameMap, Move.LEFT, 0, 6);
        gameMap.ghosts[2] = new HardGhost(1, 25, gameMap, Move.RIGHT, 4, 0);
        gameMap.ghosts[3] = new HardGhost(25, 25, gameMap, Move.DOWN, 4, 6);
    }

    private void extractData(HashMap<String, Object> data) {

        this.gameMap = new GameMap(this, ((PreMap) data.get("map")).map);
        if ((Boolean) data.get("is hard")) createHardGhost();
        else createEasyGhost();


        gameMap.pacman = new Pacman(14, 14, gameMap);
        gameMap.pacman.life = ((Integer) data.get("life amount"));
    }

    public void gameOver() {
        Dialog dialog = new Dialog("Game Over", mainClass.skin2Json, "dialog") {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    waitingScreen = ScreenType.USER;
                    MainClass.isSuccessful = true;
                }
            }
        }.text("your score: " + gameMap.pacman.score);
        dialog.button("Ok", true); //sends "true" as the result
        dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        dialog.show(stage);
    }
}
