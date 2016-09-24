package com.bitdecay.blacknickel.util;

import com.badlogic.gdx.Gdx;

/**
 * Helps to allow for multi-key checks on JustPressed and Pressed.
 */
public final class InputHelper {
    private InputHelper(){}

    public static boolean isKeyJustPressed(int... keyboardKeys){
        for (int keyboardKey : keyboardKeys) if (Gdx.input.isKeyJustPressed(keyboardKey)) return true;
        return false;
    }

    public static boolean isKeyPressed(int... keyboardKeys){
        for (int keyboardKey : keyboardKeys) if (Gdx.input.isKeyPressed(keyboardKey)) return true;
        return false;
    }
}
