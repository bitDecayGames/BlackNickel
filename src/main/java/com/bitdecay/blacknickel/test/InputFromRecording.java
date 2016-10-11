package com.bitdecay.blacknickel.test;

import com.bitdecay.blacknickel.trait.IInputAware;
import com.bitdecay.blacknickel.util.InputHelper;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class InputFromRecording implements IInputAware {

    private final static Logger log = Logger.getLogger(InputFromRecording.class);

    private List<InputRecorderState> inputFrames = new ArrayList<>();
    private boolean active = false;
    private int currentFrame = 0;
    private IInputAware previousInputAware = null;

    public InputFromRecording(String serializedInput){
        throw new NotImplementedException();
    }

    public InputFromRecording(List<InputRecorderState> inputFrames){
        this.inputFrames = inputFrames;
    }

    public boolean isActive(){ return active; }

    public InputFromRecording startInputProcessing(){
        if (previousInputAware == null) previousInputAware = InputHelper.getInputAware();
        InputHelper.setInputAware(this);
        active = true;
        currentFrame = 0;
        return this;
    }

    public InputFromRecording stopInputProcessing(){
        if (previousInputAware != null) InputHelper.setInputAware(previousInputAware);
        active = false;
        currentFrame = 0;
        return this;
    }

    public InputFromRecording update(){
        if (active){
            currentFrame++;
            if (currentFrame >= inputFrames.size()) stopInputProcessing();
        }
        return this;
    }

    @Override
    public boolean isKeyJustPressed(int key) {
        return currentFrame > 0 && isKeyPressedAtFrame(currentFrame, key) && !isKeyPressedAtFrame(currentFrame - 1, key);
    }

    @Override
    public boolean isKeyPressed(int key) {
        return isKeyPressedAtFrame(currentFrame, key);
    }

    private boolean isKeyPressedAtFrame(int frame, int key){
        return active && inputFrames.get(frame).keyStates.get(key).state;
    }
}
