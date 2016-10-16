package com.bitdecay.blacknickel.screen;

import com.badlogic.gdx.Gdx;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.room.TestingRunRoom;
import com.bitdecay.jump.level.FileUtils;
import com.bitdecay.jump.level.Level;
import com.typesafe.config.Config;

import java.util.List;

/**
 * This scene is meant to run test scenes
 */
public class TestRunScreen extends GameScreen {

    private static List<? extends Config> tests = Launcher.conf.getConfigList("testScenes");
    private static int currentTest = 0;

    public TestRunScreen(MyGame game){
        this(game, new TestingRunRoom(FileUtils.loadFileAs(Level.class, Gdx.files.classpath(tests.get(currentTest).getString("level")).readString()), tests.get(currentTest)));
    }
    public TestRunScreen(MyGame game, AbstractRoom room){
        super(game, room);
    }

    public AbstractRoom nextTest(){
        currentTest++;
        if (currentTest >= tests.size()) currentTest = 0;
        AbstractRoom room = new TestingRunRoom(FileUtils.loadFileAs(Level.class, Gdx.files.classpath(tests.get(currentTest).getString("level")).readString()), tests.get(currentTest));
        setRoom(room);
        return room;
    }
}
