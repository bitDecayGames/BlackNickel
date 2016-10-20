package com.bitdecay.blacknickel.room;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.StringBuilder;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.component.TextComponent;
import com.bitdecay.blacknickel.test.TestZones;
import com.bitdecay.jump.level.Level;
import com.bytebreakstudios.input.InputRecorder;
import com.bytebreakstudios.input.InputRecording;
import com.bytebreakstudios.input.Key;
import com.bytebreakstudios.input.Keyboard;


/**
 * This room is meant for testing purposes, setting up tests and running them.
 */
public class TestingRoom extends GenericRoom {

    protected InputRecorder recorder = new InputRecorder();
    protected InputRecording recording = null;
    protected TestZones testZones = new TestZones();
    protected TextComponent debugText;
    protected boolean paused = true;

    public TestingRoom(Level level) {
        super(level);
    }

    @Override
    public void update(float delta) {
        if (!paused) super.update(delta);
        // input recording
        if (recording == null){
            if (Keyboard.isKeyJustPressed(Key.ENTER)){
                paused = false;
                if (! recorder.isRecording()) {
                    debugText.color = Color.RED.cpy();
                    debugText.text = "Recording (Press ENTER to stop)";
                    recorder.startRecording();
                }
                else {
                    debugText.color = Color.GREEN.cpy();
                    debugText.text = "Stopped (Press ENTER to run) (Press ESCAPE to serialize)";
                    recorder.stopRecording();
                    recording = recorder.generateNewInputProcessor();
                    recorder = new InputRecorder();
                }
            }
        } else if (!recording.isActive()) {
            if (Keyboard.isKeyJustPressed(Key.ENTER)) {
                debugText.color = Color.RED.cpy();
                debugText.text = "Running";
                recording.startInputProcessing();
            }
        } else recording.update();
        recorder.update();

        testZones.update();

        if (Keyboard.isKeyJustPressed(Key.ESCAPE)) log.debug("\n\n" + serialize() + "\n");
    }

    @Override
    public void render(OrthographicCamera cam) {
        super.render(cam);
        testZones.render(cam);
    }

    @Override
    public void levelChanged(Level level) {
        super.levelChanged(level);
        getGameObjects().addNew((gob) -> {
            new PositionComponent(gob, 0, 0).addSelfToGameObject();
            debugText = new TextComponent(gob, "PAUSED: Press ENTER to begin recording", Color.GREEN.cpy(), 1);
            debugText.addSelfToGameObject();
        });
        getGameObjects().addNew((gob) -> {
            new PositionComponent(gob, 0, 20).addSelfToGameObject();
            new TextComponent(gob, "Hold COMMA for START\nHold PERIOD for END", Color.WHITE.cpy(), 1).addSelfToGameObject();
        });
        getGameObjects().cleanup();
    }


    public String serialize(){
        StringBuilder sb = new StringBuilder();
        if (recording != null){
            sb.append("level = \"").append(Launcher.conf.getString("test.testSetupLevel")).append("\"\n\n");
            sb.append("recording = ").append(recording.serialize()).append("\n\n");
            sb.append("zones = ").append(testZones.serialize());
        }
        return sb.toString();
    }
}
