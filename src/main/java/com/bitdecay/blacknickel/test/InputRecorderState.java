package com.bitdecay.blacknickel.test;

import com.bitdecay.blacknickel.util.InputHelper;

import java.util.ArrayList;
import java.util.List;

public class InputRecorderState {

    public List<KeyPressState> keyStates = new ArrayList<>();

    public InputRecorderState(){
        for (int i = 0; i < 256; i ++) keyStates.add(new KeyPressState(i));
    }

    @Override
    public String toString() {
        return keyStates.toString();
    }

    public class KeyPressState{
        public int key;
        public boolean state;
        public KeyPressState(int key){
            this.key = key;
            this.state = InputHelper.isKeyPressed(key);
        }

        @Override
        public String toString(){
            return "{\"key\": " + key + ", \"state\": " + state + " }";
        }
    }
}

