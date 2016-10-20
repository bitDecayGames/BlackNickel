package com.bitdecay.blacknickel.input;

import com.bitdecay.jump.gdx.input.KeyState;
import com.bytebreakstudios.input.Key;
import com.bytebreakstudios.input.Keyboard;

import java.util.List;

/**
 * For use with jump controls
 */
public class MultiKeyState extends KeyState {

    private List<Key> keys;

    public MultiKeyState(int... keys) {
        super(0);
        this.keys = Key.fromCodes(keys);
    }

    public MultiKeyState(List<Integer> keys){
        super(0);
        this.keys = Key.fromCodes(keys);
    }

    @Override
    public boolean isJustPressed() {
        return Keyboard.isAtLeastOneKeyJustPressed(keys);
    }

    @Override
    public boolean isPressed() {
        return Keyboard.isAtLeastOneKeyPressed(keys);
    }
}
