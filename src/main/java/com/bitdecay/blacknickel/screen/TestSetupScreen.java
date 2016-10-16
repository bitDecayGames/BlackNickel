package com.bitdecay.blacknickel.screen;

import com.badlogic.gdx.Gdx;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.room.TestingRoom;
import com.bitdecay.jump.level.FileUtils;
import com.bitdecay.jump.level.Level;

/**
 * This screen is meant to set up test scenarios
 */
public class TestSetupScreen extends GameScreen {

    public TestSetupScreen(MyGame game){
        this(game, new TestingRoom(FileUtils.loadFileAs(Level.class, Gdx.files.classpath(Launcher.conf.getString("testSetupLevel")).readString())));
    }
    public TestSetupScreen(MyGame game, AbstractRoom room){
        super(game, room);
    }
}
