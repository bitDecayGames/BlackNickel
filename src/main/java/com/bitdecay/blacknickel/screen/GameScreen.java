package com.bitdecay.blacknickel.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.gameobject.MyGameObjectFactory;
import com.bitdecay.blacknickel.room.DemoRoom;
import com.bitdecay.blacknickel.trait.ICanSetRoom;
import com.bitdecay.blacknickel.trait.ICanSetScreen;
import com.bitdecay.blacknickel.trait.IHasScreenSize;
import com.bitdecay.blacknickel.util.SoundLibrary;
import com.bitdecay.blacknickel.util.Tilesets;
import com.bitdecay.jump.collision.BitWorld;
import com.bitdecay.jump.gdx.level.EditorIdentifierObject;
import com.bitdecay.jump.gdx.level.RenderableLevelObject;
import com.bitdecay.jump.level.Level;
import com.bitdecay.jump.leveleditor.EditorHook;
import com.bitdecay.jump.leveleditor.utils.LevelUtilities;

import java.util.Collections;
import java.util.List;

/**
 * The game screen used to be the main source of game logic.  It is now more just like any other screen.  It allows for the game to switch to it, but the main logic is moved into the Room class.  In the same way you can switch from screen to screen with a reference to the MyGame object, you can switch from room to room with the GameScreen object.
 */
public class GameScreen implements Screen, EditorHook, IHasScreenSize, ICanSetScreen, ICanSetRoom {

    private MyGame game;

    private com.bitdecay.blacknickel.room.AbstractRoom room;

    public GameScreen(MyGame game){
        this.game = game;
        setRoom(new DemoRoom(this, LevelUtilities.loadLevel("src/main/resources/level/simple.level")));
    }
    public GameScreen(MyGame game, com.bitdecay.blacknickel.room.AbstractRoom room){
        this.game = game;
        setRoom(room);
    }

    @Override
    public void show() {
        SoundLibrary.stopMusic(Launcher.conf.getString("splash.music"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (room != null) room.render(delta);
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
        if (room != null) room.dispose();
    }

    @Override
    public Vector2 screenSize() {
        return null; // TODO: need to implement a way to change the screen size
    }

    @Override
    public void setScreen(Screen screen) {
        game.setScreen(screen);
    }

    @Override
    public void setRoom(com.bitdecay.blacknickel.room.AbstractRoom room) {
        if (this.room != null) this.room.dispose();
        this.room = room;
    }

    // ////////////////////////////////////
    // Level editor hook methods
    // ////////////////////////////////////

    @Override
    public void update(float delta) {
        if (room != null) room.update(delta);
    }

    @Override
    public void render(OrthographicCamera orthographicCamera) {
        if (room != null) room.render(orthographicCamera);
    }

    @Override
    public BitWorld getWorld() {
        if (room != null) return room.getWorld();
        else return null;
    }

    @Override
    public List<EditorIdentifierObject> getTilesets() {
        return Tilesets.editorTilesets();
    }

    @Override
    public List<RenderableLevelObject> getCustomObjects() {
        return MyGameObjectFactory.allLevelObjects();
    }

    @Override
    public List<EditorIdentifierObject> getThemes() { return Collections.emptyList(); }

    @Override
    public void levelChanged(Level level) {
        if (room != null) room.levelChanged(level);
    }
}
