package com.bitdecay.blacknickel.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Keyboard {

    private static final int MAX_FRAMES = 100;
    private static List<KeyboardState> states = new ArrayList<>();
    private static IInputAware inputAware = new GdxInputAware();

    private Keyboard(){}

    public static IInputAware getInputAware(){ return inputAware; }

    public static void setInputAware(IInputAware inputAware){
        if (inputAware == null) throw new RuntimeException("Input object cannot be null");
        Keyboard.inputAware = inputAware;
    }

    public static void step(){
        states.add(0, new KeyboardState(inputAware));
        if (states.size() > MAX_FRAMES) states.remove(states.size() - 1);
    }

    public static KeyboardState current(){
        if (states.size() <= 0) step();
        return states.get(0);
    }

    public static KeyboardState previous(){
        if (states.size() <= 1) {
            step();
            step();
        }
        return states.get(1);
    }

    public static boolean isKeyPressed(Key key) {
        return current().isKeyPressed(key);
    }

    public static boolean isKeyJustPressed(Key key){
        return current().isKeyPressed(key) && previous().isKeyReleased(key);
    }

    public static boolean isKeyReleased(Key key){
        return current().isKeyReleased(key);
    }

    public static boolean isKeyJustReleased(Key key){
        return current().isKeyReleased(key) && previous().isKeyPressed(key);
    }

    public static boolean areAllKeysPressed(Key... keys){
        return current().areAllKeysPressed(keys);
    }

    public static boolean areAllKeysPressed(Collection<Key> keys){
        return current().areAllKeysPressed(keys);
    }

    public static boolean areAllKeysReleased(Key... keys){
        return current().areAllKeysReleased(keys);
    }

    public static boolean areAllKeysReleased(Collection<Key> keys){
        return current().areAllKeysReleased(keys);
    }

    public static boolean isAtLeastOneKeyPressed(Key... keys){
        return current().isAtLeastOneKeyPressed(keys);
    }

    public static boolean isAtLeastOneKeyPressed(Collection<Key> keys){
        return current().isAtLeastOneKeyPressed(keys);
    }

    public static boolean isAtLeastOneKeyJustPressed(Key... keys){
        for (Key key : keys) if (isKeyJustPressed(key)) return true;
        return false;
    }

    public static boolean isAtLeastOneKeyJustPressed(Collection<Key> keys){
        for (Key key : keys) if (isKeyJustPressed(key)) return true;
        return false;
    }

    public static boolean isAtLeastOneKeyJustReleased(Key... keys){
        for (Key key : keys) if (isKeyJustReleased(key)) return true;
        return false;
    }

    public static boolean isAtLeastOneKeyJustReleased(Collection<Key> keys){
        for (Key key : keys) if (isKeyJustReleased(key)) return true;
        return false;
    }
}
