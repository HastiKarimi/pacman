package com.pacman.menues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.pacman.model.User;

public class UserMenu extends ScreenAdapter {
    MainClass mainClass;
    Stage stage;
    TextButton buttonLogout, buttonPlay, buttonShowScoreboard, buttonChangePassword, buttonDeleteAccount;
    Table table;
    User user;

    public UserMenu(MainClass mainClass, User user) {
        this.stage = new Stage(new ExtendViewport(1024, 1024));
        this.mainClass = mainClass;
        this.user = user;
    }

    @Override
    public void show() {
        table = new Table();
        table.setFillParent(true);

        buttonPlay = mainClass.createButton("Play");
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO
            }
        });
        table.add(buttonPlay).minWidth(300).expandX();
        table.row();


        buttonShowScoreboard = mainClass.createButton("Scoreboard");
        buttonShowScoreboard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO
            }
        });
        table.add(buttonShowScoreboard).minWidth(300).expandX();
        table.row();

        buttonChangePassword = mainClass.createButton("Change Password");
        buttonChangePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreenToChangePasswordMenu(user);
            }
        });
        table.add(buttonChangePassword).minWidth(300).expandX();
        table.row();


        buttonDeleteAccount = mainClass.createButton("Delete Account");
        buttonDeleteAccount.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO
            }
        });
        table.add(buttonDeleteAccount).minWidth(300).expandX();
        table.row();


        buttonLogout = mainClass.createButton("Log out");   //done
        buttonLogout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreenToMainMenu();
            }
        });
        table.add(buttonLogout).minWidth(300);
        table.row();


        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
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
