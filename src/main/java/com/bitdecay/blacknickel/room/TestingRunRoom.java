package com.bitdecay.blacknickel.room;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.test.InputRecording;
import com.bitdecay.blacknickel.test.TestZones;
import com.bitdecay.jump.level.Level;
import com.typesafe.config.Config;

/**
 * This room is meant for testing purposes, setting up tests and running them.
 */
public class TestingRunRoom extends GenericRoom {

    protected InputRecording recording = null;
    protected TestZones testZones = null;

    public TestingRunRoom(Level level) {
        super(level);
        reset();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        recording.update();
        testZones.update();
    }

    @Override
    public void render(OrthographicCamera cam) {
        super.render(cam);
        testZones.render(cam);
    }

    public void reset(){
        levelChanged(this.level);

        Config conf = Launcher.conf.getConfig("testScene");
        recording = new InputRecording(conf.getConfig("recording"));
        testZones = new TestZones(conf.getConfig("zones"));

        recording.startInputProcessing(this::reset);
    }
}
