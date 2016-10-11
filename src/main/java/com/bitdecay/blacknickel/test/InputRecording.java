package com.bitdecay.blacknickel.test;

import com.bitdecay.blacknickel.trait.IInputAware;
import com.bitdecay.blacknickel.util.InputHelper;
import com.typesafe.config.Config;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InputRecording implements IInputAware {

    private final static Logger log = Logger.getLogger(InputRecording.class);

    private List<InputRecorderState> inputFrames = new ArrayList<>();
    private boolean active = false;
    private int currentFrame = 0;
    private IInputAware previousInputAware = null;
    private Runnable onStop = null;

    public InputRecording(Config conf){
        int size = conf.getInt("size");
        List<? extends Config> dataConf = conf.getConfigList("data");
        List<Optional<List<Integer>>> data = new ArrayList<>();
        for (int i = 0; i < size; i++) data.add(Optional.empty());
        dataConf.forEach(frameConf -> {
            int frame = frameConf.getInt("frame");
            List<Integer> frameData = frameConf.getIntList("data");
            data.set(frame, Optional.of(frameData));
        });
        data.forEach(frame -> inputFrames.add(new InputRecorderState(frame.orElse(new ArrayList<>()))));
    }

    public InputRecording(List<InputRecorderState> inputFrames){
        this.inputFrames = inputFrames;
    }

    public boolean isActive(){ return active; }

    public InputRecording startInputProcessing(Runnable onStop){
        this.onStop = onStop;
        if (previousInputAware == null) previousInputAware = InputHelper.getInputAware();
        InputHelper.setInputAware(this);
        active = true;
        currentFrame = 0;
        return this;
    }

    public InputRecording startInputProcessing(){
        return startInputProcessing(null);
    }

    public InputRecording stopInputProcessing(){
        if (previousInputAware != null) InputHelper.setInputAware(previousInputAware);
        active = false;
        currentFrame = 0;
        if (onStop != null) onStop.run();
        return this;
    }

    public InputRecording update(){
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
        return active && inputFrames.get(frame).keyStates[key];
    }

    public String serialize(){
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("\"size\": ").append(inputFrames.size()).append(", ");
        sb.append("\"data\": ");
        List<String> data = new ArrayList<>();
        for (int i = 0; i < inputFrames.size(); i++){
            InputRecorderState frame = inputFrames.get(i);
            String frameStr = frame.toString();
            if (! frameStr.equalsIgnoreCase("[]")){
                data.add("{\"frame\": " + i + ", \"data\": " + frameStr + "}");
            }
        }
        sb.append(data);
        sb.append(" }");
        return sb.toString();
    }

    @Override
    public String toString(){
        return serialize();
    }
}
