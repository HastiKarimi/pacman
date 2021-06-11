package com.pacman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.pacman.MainClass;
import com.pacman.model.User;
import com.pacman.model.Wallpaper;

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
                mainClass.setScreenToPreGameScreen(user);
            }
        });
        table.add(buttonPlay).minWidth(400).expandX().padTop(100);
        table.row();


        buttonShowScoreboard = mainClass.createButton("Scoreboard");
        buttonShowScoreboard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreenToScoreboardMenu(user);
            }
        });
        table.add(buttonShowScoreboard).minWidth(400).expandX();
        table.row();

        buttonChangePassword = mainClass.createButton("Change Password");
        buttonChangePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreenToChangePasswordMenu(user);
            }
        });
        table.add(buttonChangePassword).minWidth(400).expandX();
        table.row();


        buttonDeleteAccount = mainClass.createButton("Delete Account");
        buttonDeleteAccount.addListener(new ClickListener() {   //done
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("confirmation", mainClass.skin2Json, "dialog"){
                    @Override
                    protected void result(Object object) {
                        if ((Boolean) object) {
                            user.deleteAccount();
                            mainClass.setScreenToMainMenu();
                        }
                    }
                }.button("Yes", true).button("No", false).
                        text("Are you sure about deleting your account?");


                dialog.align(Align.center);
                dialog.setSize(400, 300);
                dialog.show(stage);
            }
        });
        table.add(buttonDeleteAccount).minWidth(400).expandX();
        table.row();


        buttonLogout = mainClass.createButton("Log out");   //done
        buttonLogout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreenToMainMenu();
            }
        });
        table.add(buttonLogout).minWidth(400);
        table.row();

        stage.addActor(new Wallpaper(1, 212,650, 600, 400));
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.32f, 0.29f, 0.26f, 1f);
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
        stage.dispose();
    }
}
