package com.bitdecay.blacknickel.input;

import com.badlogic.gdx.Gdx;
import com.bitdecay.blacknickel.trait.IDeserializable;
import com.bitdecay.blacknickel.trait.ISerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class MouseState implements ISerializable, IDeserializable {

    private List<MouseButton> down = new ArrayList<>();

    public MouseState(IInputAware input){
        MouseButton.stream().filter(btn -> Gdx.input.isButtonPressed(btn.code)).forEach(down::add);
    }

    public MouseState(List<Integer> data){
        deserialize(data);
    }

    public MouseState(String data){
        deserialize(data);
    }

    @Override
    public String toString() {
        return serialize();
    }

    @Override
    public String serialize() {
        return down.stream().map(key -> key.code).collect(Collectors.toList()).toString();
    }

    @Override
    public void deserialize(String data) {
        deserialize(Arrays.stream(data.replace("[", "").replace("]", "").replace(" ", "").split(",")).map(Integer::getInteger).collect(Collectors.toList()));
    }

    public void deserialize(List<Integer> data){
        data.forEach(code -> MouseButton.fromCode(code).ifPresent(down::add));
    }

    public boolean isButtonPressed(MouseButton btn){
        return down.contains(btn);
    }

    public boolean isButtonReleased(MouseButton btn){
        return !isButtonPressed(btn);
    }

    public boolean areAllButtonsPressed(MouseButton... btns){
        return down.containsAll(Arrays.asList(btns));
    }

    public boolean areAllButtonsPressed(Collection<MouseButton> btns){
        return down.containsAll(btns);
    }

    public boolean areAllButtonsReleased(MouseButton... btns){
        for (MouseButton btn : btns) if (isButtonPressed(btn)) return false;
        return true;
    }

    public boolean areAllButtonsReleased(Collection<MouseButton> btns){
        for (MouseButton btn : btns) if (isButtonPressed(btn)) return false;
        return true;
    }

    public boolean isAtLeastOneButtonPressed(MouseButton... btns){
        for (MouseButton btn : btns) if (isButtonPressed(btn)) return true;
        return false;
    }

    public boolean isAtLeastOneButtonPressed(Collection<MouseButton> btns){
        for (MouseButton btn : btns) if (isButtonPressed(btn)) return true;
        return false;
    }

    public boolean isAtLeastOneButtonReleased(MouseButton... btns){
        for (MouseButton btn : btns) if (isButtonReleased(btn)) return true;
        return false;
    }

    public boolean isAtLeastOneButtonReleased(Collection<MouseButton> btns){
        for (MouseButton btn : btns) if (isButtonReleased(btn)) return true;
        return false;
    }
}
