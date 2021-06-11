package com.pacman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pacman.MainClass;
import com.pacman.controller.SignUpMenuController;
import com.pacman.model.Wallpaper;
import com.pacman.tools.Pair;


public class SignUpMenu extends ScreenAdapter {
    SignUpMenuController signUpMenuController;
    MainClass mainClass;
    Stage stage;
    TextButton buttonRegister, buttonBack;
    Label usernameLabel, passwordLabel, confirmPasswordLabel;
    TextField usernameTextField, passwordTextField, confirmPasswordTextField;
    Table table;
    ScreenType waitingScreen;


    {
        signUpMenuController = new SignUpMenuController(this);
        this.stage = new Stage(new StretchViewport(1024, 1024));
    }

    public SignUpMenu(MainClass mainClass) {
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
        confirmPasswordLabel = mainClass.createLabel("confirm: ");
        confirmPasswordTextField = mainClass.createTextField("");

        table.add(usernameLabel).padTop(100f);
        table.add(usernameTextField).width(300).height(60).padTop(100f).row();
        table.add(passwordLabel);
        table.add(passwordTextField).width(300).height(60).row();
        table.add(confirmPasswordLabel);
        table.add(confirmPasswordTextField).width(300).height(60).row();

        buttonRegister = mainClass.createButton("Register");
        buttonRegister.padTop(20);
        buttonRegister.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Pair answer = signUpMenuController.processInfo(
                        usernameTextField.getText(), passwordTextField.getText()
                        , confirmPasswordTextField.getText());

                if (!answer.first) {
                    showMessage(answer.second, false);
                    waitingScreen = ScreenType.MAIN;
                } else {
                    showMessage(answer.second, true);
                    usernameTextField.setText("");
                    passwordTextField.setText("");
                    confirmPasswordTextField.setText("");
                }
            }
        });
        table.add(buttonRegister).colspan(2).width(250).align(Align.center).padTop(70f);
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

        if (MainClass.isSuccessful && waitingScreen == null) {
            MainClass.isSuccessful = false;
        }
        if (MainClass.isSuccessful && waitingScreen == ScreenType.MAIN) {
            MainClass.isSuccessful = false;
            mainClass.setScreenToMainMenu();
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

    public void showMessage(String message, boolean isWarning) {
        mainClass.createDialog(message, isWarning, stage);
    }
}
