package com.pacman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.pacman.model.User;
import com.pacman.screens.game.GameScreen;


public class MainClass extends Game {
    public Stage stage;
    public BitmapFont whiteFont;
    public BitmapFont blackFont;
    public TextureAtlas textureAtlas1;   //neon
    public Skin skin1;
    public TextureAtlas textureAtlas2;   //freezing
    public Skin skin2;
    public Skin skin2Json;
    public static boolean isSuccessful;

    @Override
    public void create() {
        setAssets();
        stage = new Stage();
//        setScreenToMainMenu();
        setScreenToGameScreen(null);
    }

    public void setScreenToSignUpMenu() {
        setScreen(new SignUpMenu(this));
    }

    public void setScreenToMainMenu() {
        setScreen(new MainMenu(this));
    }

    public void setScreenToLoginMenu() {
        setScreen(new LoginMenu(this));
    }

    public void setScreenToUserMenu(User user) {
        setScreen(new UserMenu(this, user));
    }

    public void setScreenToChangePasswordMenu(User user) {
        setScreen(new ChangePasswordMenu(this, user));
    }

    public void setScreenToPlayMenu(User user) {
        //TODO
    }

    public void setScreenToGameScreen(User user) {
        setScreen(new GameScreen(this, user));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        whiteFont.dispose();
        blackFont.dispose();
        skin2Json.dispose();
        skin1.dispose();
        skin2.dispose();
        textureAtlas1.dispose();
        textureAtlas2.dispose();
        super.dispose();
    }

    public Label createLabel(String text) {
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = whiteFont;
        Label label = new Label(text, labelStyle);
        label.setAlignment(Align.center);
        label.setFontScale(1.2f);
        return label;
    }

    public TextButton createButton(String text) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("freezing/raw/font-title-export.fnt"), false);
        textButtonStyle.over = skin1.getDrawable("button-over");
        textButtonStyle.down = skin1.getDrawable("button-pressed");
        textButtonStyle.up = skin1.getDrawable("button");

        TextButton textButton = new TextButton(text, textButtonStyle);
        textButton.pad(20);
        return textButton;
    }

    public TextField createTextField(String text) {
        //TODO try to fix the bug
        Skin skin = new Skin(Gdx.files.internal("freezing/skin/freezing-ui.json"));
//        TextFieldStyle textFieldStyle = new TextFieldStyle();
//        textFieldStyle.font = blackFont;
//        textFieldStyle.fontColor = Color.BLACK;
//        textFieldStyle.cursor = skin1.newDrawable("cursor", Color.GREEN);
//        textFieldStyle.cursor.setMinWidth(2f);
////        tStyle.cursor = skin.newDrawable("cursor", Color.GREEN);
////        tStyle.cursor.setMinWidth(2f);
//        textFieldStyle.selection = skin1.getDrawable("selection");
//        textFieldStyle.background = skin1.getDrawable("textfield");
        TextField textField = new TextField(text, skin);
        return textField;
    }

    public boolean createDialog(String message, boolean isWarning, Stage stage) {
        //TODO success box doesn't show.
        Dialog dialog;
        MainClass.isSuccessful = false;
        String title = "Success Message";
        if (isWarning) title = "Error";

        dialog = new Dialog(title, skin2Json, "dialog"){
            @Override
            protected void result(Object object) {
                MainClass.isSuccessful = true;
            }
        };
        dialog.getBackground().setMinWidth(400);
        dialog.getBackground().setMinHeight(200);
        dialog.text(message);
        dialog.button("Ok", true); //sends "true" as the result
        dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        dialog.show(stage);

        return MainClass.isSuccessful;
    }

    public void setAssets() {
        whiteFont = new BitmapFont(Gdx.files.internal("freezing/raw/font-title-export.fnt"), false);
        whiteFont.getData().setScale(2f);
        blackFont = new BitmapFont(Gdx.files.internal("freezing/raw/font-export.fnt"), false);
        blackFont.setColor(Color.BLACK);
        textureAtlas1 = new TextureAtlas(Gdx.files.internal("neon/skin/neon-ui.atlas"));
        skin1 = new Skin(textureAtlas1);
        textureAtlas2 = new TextureAtlas(Gdx.files.internal("freezing/skin/freezing-ui.atlas"));
        skin2 = new Skin(textureAtlas2);
        skin2Json = new Skin(Gdx.files.internal("freezing/skin/freezing-ui.json"));
    }
}