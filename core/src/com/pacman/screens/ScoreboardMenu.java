package com.pacman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pacman.MainClass;
import com.pacman.model.User;

import java.util.ArrayList;

public class ScoreboardMenu implements Screen {
    Stage stage;
    MainClass mainClass;
    User user;
    ArrayList<User> allUsers;
    Label[] usernames;
    Label[] ranks;
    Label[] score;
    Table table;

    public ScoreboardMenu(MainClass mainClass, User user) {
        this.mainClass = mainClass;
        this.user = user;
        stage = new Stage(new StretchViewport(1024, 1024));
        allUsers = User.sortUsersForScoreBoard();
    }

    @Override
    public void show() {
        table = new Table();
        table.setFillParent(true);
        table.align(Align.top);
        table.padTop(100f);
        usernames = new Label[11];
        ranks = new Label[11];
        score = new Label[11];
        createLabelsForScoreBoard();
        stage.addActor(table);
        stage.addActor(mainClass.createBackButton(new UserMenu(mainClass, user)));

        Gdx.input.setInputProcessor(stage);
    }

    private void addInfoToTable(Table table, int index) {
        table.add(ranks[index]).padRight(100f);
        table.add(usernames[index]).padRight(100f);
        table.add(score[index]).row();
    }

    private void createLabelsForScoreBoard() {
        usernames[0] = mainClass.createLabel("#username");
        ranks[0] = mainClass.createLabel("#rank");
        score[0] = mainClass.createLabel("#score");
        addInfoToTable(table, 0);
        int j = 1;
        for (int i = 0; i < 10; i++) {
            if (i < allUsers.size()) {
                putLabelInfo(i, j);
                try {
                    if (!(allUsers.get(i).highScore == allUsers.get(i + 1).highScore))
                        j++;
                } catch (IndexOutOfBoundsException ignore) {
                }
            }
        }
    }

    private void putLabelInfo(int i, int j) {
        User thisUser = allUsers.get(i);
        usernames[i + 1] = mainClass.createLabel(thisUser.username);
        score[i + 1] = mainClass.createLabel(String.valueOf(thisUser.highScore));
        ranks[i + 1] = mainClass.createLabel(String.valueOf(j));

        addInfoToTable(table, i + 1);
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
