package com.bitdecay.blacknickel.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Mouse {
    private static final int MAX_FRAMES = 100;
    private static List<MouseState> states = new ArrayList<>();
    private static IInputAware inputAware = new GdxInputAware();

    private Mouse(){}

    public static void setInputAware(IInputAware inputAware){
        if (inputAware == null) throw new RuntimeException("Input object cannot be null");
        Mouse.inputAware = inputAware;
    }

    public static void step(){
        states.add(0, new MouseState(inputAware));
        if (states.size() > MAX_FRAMES) states.remove(states.size() - 1);
    }

    public static MouseState current(){
        if (states.size() <= 0) step();
        return states.get(0);
    }

    public static MouseState previous(){
        if (states.size() <= 1) {
            step();
            step();
        }
        return states.get(1);
    }

    public static boolean isButtonPressed(MouseButton btn) {
        return current().isButtonPressed(btn);
    }

    public static boolean isKeyJustPressed(MouseButton btn){
        return current().isButtonPressed(btn) && previous().isButtonReleased(btn);
    }

    public static boolean isButtonReleased(MouseButton btn){
        return current().isButtonReleased(btn);
    }

    public static boolean isKeyJustReleased(MouseButton btn){
        return current().isButtonReleased(btn) && previous().isButtonPressed(btn);
    }

    public static boolean areAllButtonsPressed(MouseButton... btns){
        return current().areAllButtonsPressed(btns);
    }

    public static boolean areAllButtonsPressed(Collection<MouseButton> btns){
        return current().areAllButtonsPressed(btns);
    }

    public static boolean areAllButtonsReleased(MouseButton... btns){
        return current().areAllButtonsReleased(btns);
    }

    public static boolean areAllButtonsReleased(Collection<MouseButton> btns){
        return current().areAllButtonsReleased(btns);
    }

    public static boolean isAtLeastOneButtonPressed(MouseButton... btns){
        return current().isAtLeastOneButtonPressed(btns);
    }

    public static boolean isAtLeastOneButtonPressed(Collection<MouseButton> btns){
        return current().isAtLeastOneButtonPressed(btns);
    }
}
