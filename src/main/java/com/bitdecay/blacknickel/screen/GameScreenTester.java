package com.bitdecay.blacknickel.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.room.TestingRunRoom;
import com.bitdecay.jump.level.FileUtils;
import com.bitdecay.jump.level.Level;
import com.typesafe.config.Config;

import java.util.List;

/**
 * The game screen used to be the main source of game logic.  It is now more just like any other screen.  It allows for the game to switch to it, but the main logic is moved into the Room class.  In the same way you can switch from screen to screen with a reference to the MyGame object, you can switch from room to room with the GameScreen object.
 */
public class GameScreenTester extends GameScreen {

    private static List<? extends Config> tests = Launcher.conf.getConfigList("testScenes");
    private static int currentTest = 0;

    public GameScreenTester(MyGame game){
        this(game, new TestingRunRoom(FileUtils.loadFileAs(Level.class, Gdx.files.classpath(tests.get(currentTest).getString("level")).readString()), tests.get(currentTest)));
    }
    public GameScreenTester(MyGame game, AbstractRoom room){
        super(game, room);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (room != null) room.render(delta);
        // this has to be done here to avoid concurrent modification errors
        if (tempRoom != null){
            if (room != null) room.dispose();
            room = tempRoom;
            tempRoom = null;
        }
    }

    public AbstractRoom nextTest(){
        currentTest++;
        if (currentTest >= tests.size()) currentTest = 0;
        AbstractRoom room = new TestingRunRoom(FileUtils.loadFileAs(Level.class, Gdx.files.classpath(tests.get(currentTest).getString("level")).readString()), tests.get(currentTest));
        setRoom(room);
        return room;
    }
}
