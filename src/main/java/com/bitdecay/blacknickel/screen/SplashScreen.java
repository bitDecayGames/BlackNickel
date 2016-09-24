package com.bitdecay.blacknickel.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.util.SoundLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * The splash screen is simple.  It parses a list of wallpapers from the conf file and then displays them in order with fade-ins and outs.  And when there are no more wallpapers, it switches to the next screen.  The only reason you should make changes to this file are to maybe change the text about skipping, or change the next screen that is set.
 */
public class SplashScreen implements Screen {

    private List<Runnable> runnables = new ArrayList<>();
    private Stage stage;
    private MyGame game;

    public SplashScreen(MyGame game){
        this.game = game;
        stage = new Stage();
        List<String> wallpaperNames = Launcher.conf.getStringList("splash.wallpapers");
        for (String wallpaperName : wallpaperNames){
            Texture t = new Texture(Gdx.files.classpath(wallpaperName));
            Image i = new Image(t);
            i.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.addActor(i);
            i.addAction(Actions.alpha(0));
            int runnableIndex = runnables.size();
            boolean isFirst = runnables.isEmpty();
            boolean isLast = runnableIndex + 1 >= wallpaperNames.size();
            Runnable r = () -> {
                // if it is the first wallpaper, wait just a bit before starting
                Action firstBuffer = isFirst ? Actions.delay(0.25f) : Actions.delay(0);
                // fade the wallpaper in and then out
                Action fadeInAndOut = Actions.sequence(
                    Actions.fadeIn(2f),
                    Actions.delay(2f),
                    Actions.fadeOut(2f));
                // if it is the last wallpaper, then go to the next screen when finished
                // if it is not the last wallpaper, then go to the next wallpaper when finished
                Action lastRunner = isLast ? Actions.run(this::nextScreen) : Actions.run(runnables.get(runnableIndex + 1));
                // add all the actions to the actor
                i.addAction(Actions.sequence(firstBuffer, fadeInAndOut, lastRunner));
            };
            runnables.add(r);
            if (isFirst) i.addAction(Actions.run(r));
        }

        Label l = new Label("Press [space] to skip", new Skin(Gdx.files.classpath("skin/skin.json")));
        l.setFontScale(2);
        l.setColor(Color.WHITE);
        stage.addActor(l);
    }

    @Override
    public void show() {
        SoundLibrary.loopMusic(Launcher.conf.getString("splash.music"));
    }

    @Override
    public void render(float delta) {
        MyGame.ASSET_MANAGER.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) nextScreen();

        stage.act();
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void nextScreen() {
        game.setScreen(new MainMenuScreen(game));
    }
}
