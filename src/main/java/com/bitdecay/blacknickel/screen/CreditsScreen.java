package com.bitdecay.blacknickel.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.util.InputHelper;

/**
 * This is the generic credits screen.  Almost everything in the credits is populated from the /resources/conf/credits.conf file.  The only reason you should be making changes to this file is to adjust the position or speed of the text.
 */
public class CreditsScreen implements Screen {

    private static String SPACE_AFTER_TITLE = "\n\n\n";
    private static String SPACE_AFTER_NAME = "\n\n";

    private MyGame game;
    private Stage stage = new Stage();

    private Image background;
    private Label lblTitle;
    private Label lblBack;
    private Label lblCredits;


    private int menuSelection;
    private boolean downIsPressed;
    private boolean upIsPressed;
    private boolean escWasPressed;


    boolean active = true;

    public CreditsScreen(MyGame game) {
        this.game = game;

        menuSelection = 0;
        downIsPressed = false;
        upIsPressed = false;
        escWasPressed = false;

        Skin skin = new Skin(Gdx.files.classpath(Launcher.conf.getString("credits.skin")));

        background = new Image(new Texture(Gdx.files.classpath(Launcher.conf.getString("credits.background"))));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        lblTitle = new Label(" Credits", skin);
        lblTitle.setFontScale(10);
        lblTitle.setFillParent(true);
        lblTitle.setAlignment(Align.top, Align.left);
        lblTitle.setColor(Color.SKY);

        lblBack = new Label("Press esc/B to go back", skin);
        lblBack.setFontScale(2);
        lblBack.setFillParent(true);
        lblBack.setAlignment(Align.bottom, Align.right);
        lblBack.setColor(Color.GRAY);

        // ///////////////////////////////////////////////
        // Add new sections to the credits here
        // ///////////////////////////////////////////////
        StringBuilder creditsStr = new StringBuilder();
        addCreditTitleAndNames(creditsStr, "Programming: ", "credits.developers");
        addCreditTitleAndNames(creditsStr, "Art: ", "credits.artists");
        addCreditTitleAndNames(creditsStr, "Sound: ", "credits.sound");
        addCreditTitleAndNames(creditsStr, "", "credits.external");

        lblCredits = new Label(creditsStr.toString(),
                skin);
        lblCredits.setFontScale(Launcher.conf.getInt("credits.fontSize"));
        lblCredits.setBounds(0, -Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lblCredits.setAlignment(Align.center, Align.bottom);
        lblCredits.setColor(Color.WHITE);


        stage.addActor(background);
        stage.addActor(lblTitle);
        stage.addActor(lblBack);
        stage.addActor(lblCredits);

        Gdx.input.setInputProcessor(stage);
    }

    private void addCreditTitleAndNames(StringBuilder sb, String title, String names){
        sb.append(title).append(SPACE_AFTER_TITLE);
        Launcher.conf.getStringList(names).forEach(name -> sb.append(name).append(SPACE_AFTER_NAME));
        sb.append(SPACE_AFTER_TITLE);
    }

    @Override
    public void show() {
        lblCredits.addAction(Actions.sequence(
                Actions.moveBy(0, -(Gdx.graphics.getHeight()*1.5f)),
                Actions.moveBy(0, Gdx.graphics.getHeight() * 4, 30),
                Actions.run(this::nextScreen)
        ));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (InputHelper.isKeyJustPressed(Input.Keys.SPACE, Input.Keys.ESCAPE, Input.Keys.B, Input.Keys.BACKSPACE)) nextScreen();
    }

    public void nextScreen(){
        game.setScreen(new MainMenuScreen(game));
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
