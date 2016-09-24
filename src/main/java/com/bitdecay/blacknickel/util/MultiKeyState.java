package com.bitdecay.blacknickel.util;

import com.bitdecay.jump.gdx.input.KeyState;

import java.util.List;

/**
 * For use with jump controls
 */
public class MultiKeyState extends KeyState {

    private int[] keys;

    public MultiKeyState(int... keys) {
        super(0);
        this.keys = keys;
    }

    public MultiKeyState(List<Integer> keys){
        super(0);
        this.keys = new int[keys.size()];
        for (int i = 0; i < keys.size(); i++) this.keys[i] = keys.get(i);
    }

    @Override
    public boolean isJustPressed() {
        return InputHelper.isKeyJustPressed(keys);
    }

    @Override
    public boolean isPressed() {
        return InputHelper.isKeyPressed(keys);
    }
}
