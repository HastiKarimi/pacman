package com.pacman;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pacman.model.PreMap;
import com.pacman.model.User;
import com.pacman.screens.*;
import com.pacman.screens.game.GameScreen;
import com.pacman.screens.game.PreGameScreen;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainClass extends Game {
    public Stage stage;
    public BitmapFont whiteFont;
    public BitmapFont blackFont;
    public TextureAtlas textureAtlas1;   //neon
    public TextureAtlas textureAtlas2;   //freezing
    public Skin skin1, skin2, skin2Json, skin3Json, skin1Json;
    public static boolean isSuccessful = false;
    Screen lastScreen;



    @Override
    public void create() {
        setAssets();
        stage = new Stage();
        setData();
        setScreenToMainMenu();
//        setScreenToGameScreen(null);
//        setScreenToPreGameScreen(null);
    }

    public void setScreenToSignUpMenu() {
        setScreen(new SignUpMenu(this));
    }

    public void setScreenToMainMenu() {
        if (lastScreen != null)
            lastScreen.dispose();

        lastScreen = new MainMenu(this);
        setScreen(lastScreen);
    }

    public void setScreenToLoginMenu() {
        lastScreen.dispose();
        lastScreen = new LoginMenu(this);
        setScreen(lastScreen);
    }

    public void setScreenToUserMenu(User user) {
        lastScreen.dispose();
        lastScreen = new UserMenu(this, user);
        setScreen(lastScreen);
    }

    public void setScreenToChangePasswordMenu(User user) {
        lastScreen.dispose();
        lastScreen = new ChangePasswordMenu(this, user);
        setScreen(lastScreen);
    }

    public void setScreenToPreGameScreen(User user) {
        lastScreen.dispose();
        lastScreen = new PreGameScreen(this, user);
        setScreen(lastScreen);
    }

    public void setScreenToGameScreen(User user, HashMap<String, Object> data) {
        lastScreen.dispose();
        lastScreen = new GameScreen(this, user, data);
        setScreen(lastScreen);
    }

    public void setScreenToScoreboardMenu(User user) {
        lastScreen.dispose();
        lastScreen = new ScoreboardMenu(this, user);
        setScreen(lastScreen);
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

    public Table createBackButton(Screen screen) {
        TextButton button = createButton("back");
        button.setBounds(50,25,150,80);
        button.pad(20).padBottom(30f);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(screen);
            }
        });
        return button;
    }

    public TextField createTextField(String text) {
        Skin skin = new Skin(Gdx.files.internal("freezing/skin/freezing-ui.json"));
        TextField textField = new TextField(text, skin);
        textField.getStyle().font = skin3Json.getFont("font-title");
        return textField;
    }

    public void createDialog(String message, boolean isWarning, Stage stage) {
        Dialog dialog;
        String title = "Success Message";
        if (isWarning) title = "Error";

        dialog = new Dialog(title, skin2Json, "dialog") {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    MainClass.isSuccessful = true;
                }
            }
        };
        dialog.getBackground().setMinWidth(400);
        dialog.getBackground().setMinHeight(200);
        dialog.text(message);
        dialog.getTitleLabel().setFontScale(2f);
        dialog.button("Ok", true); //sends "true" as the result
        dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        dialog.show(stage);
    }

    public void setAssets() {
        whiteFont = new BitmapFont(Gdx.files.internal("freezing/raw/font-title-export.fnt"), false);
        whiteFont.getData().setScale(2f);
        blackFont = new BitmapFont(Gdx.files.internal("freezing/raw/font-export.fnt"), false);
        blackFont.setColor(Color.BLACK);
        textureAtlas1 = new TextureAtlas(Gdx.files.internal("neon/skin/neon-ui.atlas"));
        skin1 = new Skin(textureAtlas1);
        skin1Json = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
        textureAtlas2 = new TextureAtlas(Gdx.files.internal("freezing/skin/freezing-ui.atlas"));
        skin2 = new Skin(textureAtlas2);
        skin2Json = new Skin(Gdx.files.internal("freezing/skin/freezing-ui.json"));
        skin3Json = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
    }

    public void setData() {
        FileHandle file = Gdx.files.local("users/users data.json");
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, User>>() {
        }.getType();
        HashMap<String, User> allUsersData = gson.fromJson(file.readString(), type);
        User.setAllUsers(allUsersData);
        setDefaultMapsData();
    }


    public void setDefaultMapsData(){
        Gson gson = new Gson();
        FileHandle file = Gdx.files.local("users/users default maps.json");
        Type type = new TypeToken<ArrayList<int[][]>>() {
        }.getType();
        PreMap.defaultMaps = gson.fromJson(file.readString(), type);
    }
}
