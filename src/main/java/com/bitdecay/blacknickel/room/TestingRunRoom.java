package com.bitdecay.blacknickel.room;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bitdecay.blacknickel.screen.GameScreenTester;
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

    protected Config conf;

    public TestingRunRoom(Level level, Config conf) {
        super(level);
        this.conf = conf;
        reset();
    }

    @Override
    public void update(float delta) {
        // should run this method multiple times every update that way the tests go faster
        for(int i = 0; i < 5; i++) {
            super.update(delta);
            recording.update();
            testZones.update();
        }
    }

    @Override
    public void render(OrthographicCamera cam) {
        super.render(cam);
        testZones.render(cam);
    }

    public void reset(){
        levelChanged(this.level);
        checkStartConditions();

        recording = new InputRecording(conf.getConfig("recording"));
        testZones = new TestZones(conf.getConfig("zones"));

        recording.startInputProcessing(()->{
            checkEndConditions();
            ((GameScreenTester) gameScreen()).nextTest();
        });
    }

    public void checkStartConditions(){
        // TODO: this method should check that all the objects are in the correct starting places
        log.info("Check start conditions");
    }

    public void checkEndConditions(){
        // TODO: this method should check that all the objects are in the correct ending places
        log.info("Check end conditions");
    }
}
