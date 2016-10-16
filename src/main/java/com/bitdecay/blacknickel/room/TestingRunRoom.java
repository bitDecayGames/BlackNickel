package com.bitdecay.blacknickel.room;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bitdecay.blacknickel.component.*;
import com.bitdecay.blacknickel.screen.TestRunScreen;
import com.bitdecay.blacknickel.test.InputRecording;
import com.bitdecay.blacknickel.test.TestZones;
import com.bitdecay.blacknickel.util.InputHelper;
import com.bitdecay.jump.level.Level;
import com.typesafe.config.Config;

/**
 * This room is meant for testing purposes, setting up tests and running them.
 */
public class TestingRunRoom extends GenericRoom {

    protected InputRecording recording = null;
    protected TestZones testZones = null;
    protected boolean paused = false;

    protected Config conf;

    public TestingRunRoom(Level level, Config conf) {
        super(level);
        this.conf = conf;
        reset();
    }

    @Override
    public void update(float delta) {
        if (!paused) {
            // should run this method multiple times every update that way the tests go faster
            for (int i = 0; i < 5; i++) {
                super.update(delta);
                recording.update();
                testZones.update();
            }
        } else if (InputHelper.isKeyJustPressed(Input.Keys.SPACE)){
            ((TestRunScreen) gameScreen()).nextTest();
        }
    }

    @Override
    public void render(OrthographicCamera cam) {
        super.render(cam);
        testZones.render(cam);
    }

    public void reset(){
        levelChanged(this.level);
        recording = new InputRecording(conf.getConfig("recording"));
        testZones = new TestZones(conf.getConfig("zones"));
        checkStartConditions();
        recording.startInputProcessing(this::checkEndConditions);
    }

    public void checkStartConditions(){
        // this method should check that all the objects are in the correct starting places
        log.info("Check start conditions and add TestableComponents");
        getGameObjects().filter((gob) -> gob.hasComponent(PositionComponent.class) &&
                !gob.hasComponent(TileComponent.class) &&
                gob.getComponent(PositionComponent.class).filter(pos ->
                        testZones.containedWithinStartingCircles(pos.x, pos.y))
                        .isPresent())
                .forEach(gob -> {
            gob.addComponent(new TestableComponent(gob));
            gob.addComponent(new CameraFollowComponent(gob)); // add this just so we can see the gob in the world
        });
        getGameObjects().cleanup();
    }

    public void checkEndConditions(){
        // this method should check that all the objects are in the correct ending places
        log.info("Check end conditions");
        getGameObjects().filter(gob -> gob.hasComponent(TestableComponent.class)).forEach(gob -> {
            gob.forEach(PositionComponent.class, pos -> {
                if (testZones.containedWithinEndingCircles(pos.x, pos.y)) gob.addComponent(new TextComponent(gob, "PASSED", Color.GREEN.cpy(), 1));
                else gob.addComponent(new TextComponent(gob, "FAILED", Color.RED.cpy(), 1));
            });
        });
        getGameObjects().cleanup();
        paused = true;
    }
}
