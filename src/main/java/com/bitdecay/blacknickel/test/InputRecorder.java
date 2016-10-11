package com.bitdecay.blacknickel.test;

import java.util.ArrayList;
import java.util.List;

public final class InputRecorder {

    public List<InputRecorderState> frameStates = new ArrayList<>();

    private boolean recording = false;
    private Runnable onStop = null;

    public boolean isRecording(){ return recording; }

    public InputRecorder startRecording(Runnable onStop){
        this.onStop = onStop;
        recording = true;
        return this;
    }

    public InputRecorder startRecording(){
        return startRecording(null);
    }

    public InputRecorder stopRecording(){
        recording = false;
        if (onStop != null) onStop.run();
        return this;
    }

    public InputRecorder update() {
        if (recording) frameStates.add(new InputRecorderState());
        return this;
    }

    public InputRecording generateNewInputProcessor(){
        return new InputRecording(frameStates);
    }
}
