package com.bitdecay.blacknickel.test;

import java.util.ArrayList;
import java.util.List;

public final class InputRecorder {

    public List<InputRecorderState> frameStates = new ArrayList<>();

    private boolean recording = false;

    public boolean isRecording(){ return recording; }

    public InputRecorder startRecording(){
        recording = true;
        return this;
    }

    public InputRecorder stopRecording(){
        recording = false;
        return this;
    }

    public InputRecorder update() {
        if (recording) frameStates.add(new InputRecorderState());
        return this;
    }

    public InputFromRecording generateNewInputProcessor(){
        return new InputFromRecording(frameStates);
    }

    public String serialize(){
        return frameStates.toString();
    }

    @Override
    public String toString(){
        return serialize();
    }
}
