package com.pacman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MainMenu extends ScreenAdapter {
    MainClass mainClass;
    Stage stage;
    TextureAtlas textureAtlas;
    TextButton buttonExit, buttonPlay, buttonLogin, buttonSignUp;
    Skin skin;
    Table table;

    public MainMenu(MainClass mainClass) {
        this.stage = new Stage(new ExtendViewport(1024, 1024));
        this.mainClass = mainClass;
    }

    @Override
    public void show() {
        textureAtlas = new TextureAtlas(Gdx.files.internal("neon/skin/neon-ui.atlas"));
        skin = new Skin(textureAtlas);
        table = new Table();
        table.setFillParent(true);

        buttonPlay = createButton("Play");
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });
        table.add(buttonPlay).width(300);
        table.row();


        buttonLogin = createButton("Login");
        buttonLogin.pad(20);
        buttonLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreenToLoginMenu();
            }
        });
        table.add(buttonLogin).width(300);
        table.row();

        buttonSignUp = createButton("Sign Up");
        buttonSignUp.pad(20);
        buttonSignUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreenToSignUpMenu();
            }
        });
        table.add(buttonSignUp).width(300);
        table.row();


        buttonExit = createButton("Exit Game");
        buttonExit.pad(20);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });
        table.add(buttonExit).width(300);
        table.row();


//        table.debug();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private TextButton createButton(String text) {
        return getButton(text, table, skin);
    }

    static TextButton getButton(String text, Table table, Skin skin) {
//        table.setBounds(0, 0, Gdx.graphics.getWidthOfMap(), Gdx.graphics.getHeightOfMap());
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("freezing/raw/font-title-export.fnt"), false);
        textButtonStyle.over = skin.getDrawable("button-over");
        textButtonStyle.down = skin.getDrawable("button-pressed");
        textButtonStyle.up = skin.getDrawable("button");

        return new TextButton(text, textButtonStyle);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.8f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
