package com.pacman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pacman.MainClass;
import com.pacman.model.Wallpaper;
import com.pacman.tools.Pair;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.pacman.controller.LoginMenuController;


public class LoginMenu extends ScreenAdapter {
    LoginMenuController loginMenuController;
    MainClass mainClass;
    Stage stage;
    TextButton buttonLogin;
    Label usernameLabel, passwordLabel;
    TextField usernameTextField, passwordTextField;
    Table table;
    public ScreenType isWaitingForScreen;

    {
        loginMenuController = new LoginMenuController(this);
        this.stage = new Stage(new StretchViewport(1024, 1024));
    }

    public LoginMenu(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void show() {
        table = new Table();
        table.setFillParent(true);

        usernameLabel = mainClass.createLabel("username: ");
        usernameTextField = mainClass.createTextField("");
        passwordLabel = mainClass.createLabel("password: ");
        passwordTextField = mainClass.createTextField("");

        table.add(usernameLabel);
        table.add(usernameTextField).width(300).height(60).row();
        table.add(passwordLabel);
        table.add(passwordTextField).width(300).height(60).row();


        buttonLogin = mainClass.createButton("Login");
        buttonLogin.padTop(20);
        buttonLogin.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Pair answer = loginMenuController.processInfo(
                        usernameTextField.getText(), passwordTextField.getText());
                if (!answer.first) {
                    isWaitingForScreen = ScreenType.USER;
                    showMessage(answer.second, false);
                } else {
                    showMessage(answer.second, true);
                    usernameTextField.setText("");
                    passwordTextField.setText("");
                }
            }
        });
        table.add(buttonLogin).colspan(2).width(250).align(Align.center).padTop(100);
        table.row();

        stage.addActor(new Wallpaper(1, 212,650, 600, 400));
        stage.addActor(table);
        stage.addActor(mainClass.createBackButton(new MainMenu(mainClass)));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.32f, 0.29f, 0.26f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (MainClass.isSuccessful && isWaitingForScreen == null) {
            MainClass.isSuccessful = false;
        }

        if (MainClass.isSuccessful && isWaitingForScreen == ScreenType.USER) {
            MainClass.isSuccessful = false;
            mainClass.setScreenToUserMenu(loginMenuController.getUser());
        }

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

    public boolean showMessage(String message, boolean isWarning) {
        mainClass.createDialog(message, isWarning, stage);
        return true;
    }
}
