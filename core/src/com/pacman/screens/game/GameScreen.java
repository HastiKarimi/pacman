package com.pacman.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pacman.MainClass;
import com.pacman.model.*;
import com.pacman.screens.ScreenType;

import java.util.HashMap;

public class GameScreen implements Screen {
    Stage stage;
    MainClass mainClass;
    User user;
    GameMap gameMap;
    public boolean isGameEnded, mainPause;
    public TextureRegion tmp_ghost[][];
    public TextureRegion tmp_pacman[][];
    Texture pauseImage;
    public float stateTime = 0f;
    public Label scoreLabel, lifeLabel;
    public ScreenType waitingScreen;
    public Sound pacmanEats, pacmanDies, ghostsDie, gameOverSound, energyBombEaten;
    public ImageButton muteButton, pauseButton;

    public GameScreen(MainClass mainClass, User user, HashMap<String, Object> data) {
        assembleAnimations();
        this.user = user;
        this.mainClass = mainClass;
        stage = new Stage(new StretchViewport(512, 512));
        extractData(data);
        pauseImage = new Texture(Gdx.files.internal("pause1.png"));
        mainPause = false;
    }


    @Override
    public void show() {
        createSounds();

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

        muteButton = new ImageButton(mainClass.skin3Json.getDrawable("image-sound"),
                null, mainClass.skin3Json.getDrawable("image-sound-off"));
        muteButton.setBounds(440,467,30,30);
        muteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (muteButton.isChecked()) {
                    gameMap.soundRatio = 0;
                    muteButton.setChecked(true);
                }
                else {
                    gameMap.soundRatio = 1;
                    muteButton.setChecked(false);
                }
            }
        });
        if (gameMap.soundRatio == 0)
            muteButton.setChecked(true);

        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseImage)));
        pauseButton.setBounds(241,7,30,30);
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainPause = true;
                Dialog dialog = new Dialog("confirmation", mainClass.skin3Json, "dialog"){
                    @Override
                    protected void result(Object object) {
                        if (!(Boolean) object) {
                            mainPause = false;
                        } else {
                            if (user == null)
                                mainClass.setScreenToMainMenu();
                            else
                                mainClass.setScreenToUserMenu(user);
                        }
                    }
                }.button("Yes", true).button("No", false).
                        text("Do you want to exit game?");

                dialog.padLeft(30).padRight(30);
                dialog.align(Align.center);
                dialog.setSize(400, 300);
                dialog.show(stage);
            }
        });

        stage.addActor(lifeLabel);
        stage.addActor(scoreLabel);
        stage.addActor(muteButton);
        stage.addActor(pauseButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.61f, 0.4f, 0.2f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += delta;
        if (!mainPause) {
            gameMap.update(Gdx.graphics.getDeltaTime());
            scoreLabel.setText("score: " + gameMap.pacman.score);
            lifeLabel.setText("life: " + gameMap.pacman.life);
        }

        stage.act(delta);
        stage.draw();

        if ((isGameEnded = isGameEnded()))
            fillMap();

        if (MainClass.isSuccessful && waitingScreen == ScreenType.USER) {
            if (user != null) {
                checkHighScore();
                mainClass.setScreenToUserMenu(user);
            } else
                mainClass.setScreenToMainMenu();
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
        ghostsDie.dispose();
        pacmanDies.dispose();
        pacmanEats.dispose();
        gameOverSound.dispose();
        energyBombEaten.dispose();
        stage.dispose();
    }

    public void assembleAnimations() {  //y:0 to 3 - 4 to 7      x:0 to 5 - 6 to 11
        Texture ghostsSheet = new Texture(Gdx.files.internal("character_ghost.png"));
        tmp_ghost = TextureRegion.split(ghostsSheet, 64, 64);

        Texture pacmanSheet = new Texture(Gdx.files.internal("character_pacman.png"));
        tmp_pacman = TextureRegion.split(pacmanSheet, 71, 71);

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
        if ((Boolean) data.get("sound ratio")) {
            gameMap.soundRatio = 0;
        }
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

    public void createSounds() {
        pacmanEats = Gdx.audio.newSound(Gdx.files.internal("sounds/pacman_eat.ogg"));
        pacmanDies = Gdx.audio.newSound(Gdx.files.internal("sounds/pacman_die.ogg"));
        ghostsDie = Gdx.audio.newSound(Gdx.files.internal("sounds/ghost_die.ogg"));
        energyBombEaten = Gdx.audio.newSound(Gdx.files.internal("sounds/energy_bomb.ogg"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pacman_death.mp3"));
    }
}
