package com.bitdecay.blacknickel.test;

import com.bitdecay.blacknickel.util.InputHelper;

import java.util.ArrayList;
import java.util.List;

public class InputRecorderState {

    public boolean[] keyStates = new boolean[256];

    public InputRecorderState(){
        for (int i = 0; i < keyStates.length; i++) keyStates[i] = InputHelper.isKeyPressed(i);
    }

    public InputRecorderState(List<Integer> data){
        for (int i = 0; i < keyStates.length; i++) keyStates[i] = false;
        data.forEach(i -> keyStates[i] = true);
    }

    @Override
    public String toString() {
        List<Integer> keys = new ArrayList<>();
        for (int i = 0; i < keyStates.length; i++) if (keyStates[i]) keys.add(i);
        return keys.toString();
    }
}

