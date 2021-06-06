package com.pacman.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pacman.model.PreMap;
import com.pacman.model.User;
import com.pacman.screens.MainClass;
import com.pacman.screens.ScreenType;

import java.util.HashMap;

public class PreGameScreen implements Screen {
    PreMap preMapActor;
    MainClass mainClass;
    Stage stage;
    User user;
    public HashMap<String, Object> data;
    public int life = 2;
    TextButton buttonCreate, buttonSave, buttonPlay;
    public ImageButton nextMap, previousMap, addLife, decreaseLife;
    public Table table1, table2, table3;
    public Label lifeAmount;
    public Skin skin;
    public CheckBox checkBox;
    public ScreenType waitingScreen;

    {
        preMapActor = new PreMap(user);
        data = new HashMap<>();
    }

    public PreGameScreen(MainClass mainClass, User user) {
        this.mainClass = mainClass;
        this.user = user;
        stage = new Stage(new StretchViewport(800, 800));
        skin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
    }
    @Override
    public void show() {

        //table1    for saving and creating
        table1 = new Table();
        table1.setBounds(400, 20, 512, 500);
        buttonCreate = new TextButton("create", skin);
        buttonCreate.padTop(1);
        buttonCreate.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                preMapActor.createNewMap();
            }
        });
        table1.add(buttonCreate).width(150).height(70).align(Align.center).padTop(3);
        table1.row();

        buttonSave = new TextButton("save", skin);
        buttonSave.padTop(1);
        buttonSave.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        table1.add(buttonSave).width(150).height(70).align(Align.center).padTop(3);
        table1.row();

        checkBox = new CheckBox(" Hard Mode", skin);
        checkBox.addListener(new ChangeListener() {
            //TODO data change when changing the screen
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.graphics.setContinuousRendering(checkBox.isChecked());
            }
        });
        table1.add(checkBox).padTop(180).row();

        buttonPlay = new TextButton("Play >>", skin);
        buttonPlay.padTop(3);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                waitingScreen = ScreenType.GAME;
            }
        });
        table1.add(buttonPlay).width(150).height(70).align(Align.center).padTop(3);
        table1.row();


        //table2    for life
        table2 = new Table();
        table2.setBounds(300, 650, 200, 100);
        addLife = new ImageButton(skin.getDrawable("image-plus"));
        addLife.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                life++;
            }
        });

        decreaseLife = new ImageButton(skin.getDrawable("image-minus"));
        decreaseLife.scaleBy(2f);
        decreaseLife.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                life--;
            }
        });

        lifeAmount = new Label("life: " + life, mainClass.skin2Json);
        lifeAmount.setFontScale(2f);
        table2.add(decreaseLife).padRight(20);
        table2.add(lifeAmount).padRight(20).padBottom(10);
        table2.add(addLife);

        //table3    for changing map
        table3 = new Table();
        table3.setBounds(0, 70, 512, 80);
        nextMap = new ImageButton(skin.getDrawable("image-right-down"));
        previousMap = new ImageButton(skin.getDrawable("image-left-down"));
        table3.add(previousMap).width(50).height(50).padRight(100);
        table3.add(nextMap).width(50).height(50);


        stage.addActor(table1);
        stage.addActor(table2);
        stage.addActor(table3);
        stage.addActor(preMapActor);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f,0.5f,0,0.6f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (life < 2)   life = 2;
        if (life > 5)   life = 5;
        lifeAmount.setText("life: " + life);

        if (waitingScreen == ScreenType.GAME) {
            saveData();
            mainClass.setScreenToGameScreen(user, data);
        }

        stage.act(delta);
        stage.draw();
    }

    public void saveData() {
        data.put("is hard", checkBox.isChecked());
        data.put("life amount", life);
        data.put("map", preMapActor);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
