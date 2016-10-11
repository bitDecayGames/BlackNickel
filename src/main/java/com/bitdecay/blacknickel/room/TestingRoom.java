package com.bitdecay.blacknickel.room;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.StringBuilder;
import com.bitdecay.blacknickel.test.InputRecorder;
import com.bitdecay.blacknickel.test.InputRecording;
import com.bitdecay.blacknickel.test.TestZones;
import com.bitdecay.blacknickel.util.InputHelper;
import com.bitdecay.jump.level.Level;

/**
 * This room is meant for testing purposes, setting up tests and running them.
 */
public class TestingRoom extends GenericRoom {

    protected InputRecorder recorder = new InputRecorder();
    protected InputRecording recording = null;
    protected TestZones testZones = new TestZones();

    public TestingRoom(Level level) {
        super(level);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // input recording
        if (recording == null){
            if (InputHelper.isKeyJustPressed(Input.Keys.ENTER)){
                if (! recorder.isRecording()) recorder.startRecording();
                else {
                    recorder.stopRecording();
                    recording = recorder.generateNewInputProcessor();
                    recorder = new InputRecorder();
                }
            }
        } else if (!recording.isActive()) {
            if (InputHelper.isKeyJustPressed(Input.Keys.ENTER)) recording.startInputProcessing();
        } else recording.update();
        recorder.update();

        testZones.update();

        if (InputHelper.isKeyJustPressed(Input.Keys.ESCAPE)) log.debug("\n\n" + serialize() + "\n");
    }

    @Override
    public void render(OrthographicCamera cam) {
        super.render(cam);
        testZones.render(cam);
    }

    public String serialize(){
        StringBuilder sb = new StringBuilder();
        if (recording != null){
            sb.append("recording = ").append(recording.serialize()).append("\n\n");
            sb.append("zones = ").append(testZones.serialize());
        }
        return sb.toString();
    }
}
