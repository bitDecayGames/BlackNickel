package com.bitdecay.blacknickel.util;

import com.bitdecay.blacknickel.trait.IInputAware;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * Helps to allow for multi-key checks on JustPressed and Pressed.
 */
public final class InputHelper {

    private final static Logger log = Logger.getLogger(InputHelper.class);

    private static IInputAware input = new GdxInputAware();

    private InputHelper(){}

    public static boolean isKeyJustPressed(int... keyboardKeys){
        for (int keyboardKey : keyboardKeys) if (input.isKeyJustPressed(keyboardKey)) return true;
        return false;
    }

    public static boolean isKeyJustPressed(Collection<Integer> keyboardKeys){
        for (Integer keyboardKey : keyboardKeys) if (input.isKeyJustPressed(keyboardKey)) return true;
        return false;
    }

    public static boolean isKeyPressed(int... keyboardKeys){
        for (int keyboardKey : keyboardKeys) if (input.isKeyPressed(keyboardKey)) return true;
        return false;
    }

    public static boolean isKeyPressed(Collection<Integer> keyboardKeys){
        for (int keyboardKey : keyboardKeys) if (input.isKeyPressed(keyboardKey)) return true;
        return false;
    }

    public static void setInputAware(IInputAware input){
        InputHelper.input = input;
    }

    public static IInputAware getInputAware(){ return InputHelper.input; }
}
