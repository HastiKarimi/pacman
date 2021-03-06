package com.pacman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.pacman.controller.ChangePasswordController;
import com.pacman.model.User;


public class ChangePasswordMenu extends ScreenAdapter { //done
    ChangePasswordController changePasswordController;
    MainClass mainClass;
    Stage stage;
    TextButton buttonContinue;
    Label prePasswordLabel, newPasswordLabel, confirmPasswordLabel;
    TextField prePasTextField, newPasTextField, confirmPasTextField;
    Table table;
    User user;

    {
        this.stage = new Stage(new StretchViewport(1024, 1024));
    }

    public ChangePasswordMenu(MainClass mainClass, User user) {
        changePasswordController = new ChangePasswordController(this, user);
        this.mainClass = mainClass;
        this.user = user;
    }

    @Override
    public void show() {
        table = new Table();
        table.setFillParent(true);

        prePasswordLabel = mainClass.createLabel("old pass: ");
        prePasTextField = mainClass.createTextField("");
        newPasswordLabel = mainClass.createLabel("new pass: ");
        newPasTextField = mainClass.createTextField("");
        confirmPasswordLabel = mainClass.createLabel("confirm: ");
        confirmPasTextField = mainClass.createTextField("");

        table.add(prePasswordLabel).minHeight(70);
        table.add(prePasTextField).minWidth(300).row();
        table.add(newPasswordLabel).minHeight(70);
        table.add(newPasTextField).minWidth(300).row();
        table.add(confirmPasswordLabel).minHeight(70);
        table.add(confirmPasTextField).minWidth(300).row();

        buttonContinue = mainClass.createButton("Continue");
        buttonContinue.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Pair answer = changePasswordController.processInfo(prePasTextField.getText(),
                        newPasTextField.getText(), confirmPasTextField.getText());
                if (!answer.first) {
                    showMessage(answer.second, false);
                } else {
                    showMessage(answer.second, true);
                    prePasTextField.setText("");
                    newPasswordLabel.setText("");
                    confirmPasTextField.setText("");
                }
            }
        });
        table.add(buttonContinue).colspan(2).minWidth(250).align(Align.center).padTop(50);
        table.row();

        stage.addActor(new Wallpaper(1, 212,650, 600, 400));
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.32f, 0.29f, 0.26f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (MainClass.isSuccessful) {
            MainClass.isSuccessful = false;
            mainClass.setScreenToUserMenu(user);
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
