package com.bitdecay.blacknickel.input;

import com.bitdecay.blacknickel.trait.IDeserializable;
import com.bitdecay.blacknickel.trait.ISerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class KeyboardState implements ISerializable, IDeserializable {
    private List<Key> down = new ArrayList<>();

    public KeyboardState(IInputAware input){
        Key.stream().filter(input::isKeyPressed).forEach(down::add);
    }

    public KeyboardState(List<Integer> data){
        deserialize(data);
    }

    public KeyboardState(String data){
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
        data.forEach(code -> Key.fromCode(code).ifPresent(down::add));
    }

    public boolean isKeyPressed(Key key){
        return down.contains(key);
    }

    public boolean isKeyReleased(Key key){
        return !isKeyPressed(key);
    }

    public boolean areAllKeysPressed(Key... keys){
        return down.containsAll(Arrays.asList(keys));
    }

    public boolean areAllKeysPressed(Collection<Key> keys){
        return down.containsAll(keys);
    }

    public boolean areAllKeysReleased(Key... keys){
        for (Key key : keys) if (isKeyPressed(key)) return false;
        return true;
    }

    public boolean areAllKeysReleased(Collection<Key> keys){
        for (Key key : keys) if (isKeyPressed(key)) return false;
        return true;
    }

    public boolean isAtLeastOneKeyPressed(Key... keys){
        for (Key key : keys) if (isKeyPressed(key)) return true;
        return false;
    }

    public boolean isAtLeastOneKeyPressed(Collection<Key> keys){
        for (Key key : keys) if (isKeyPressed(key)) return true;
        return false;
    }

    public boolean isAtLeastOneKeyReleased(Key... keys){
        for (Key key : keys) if (isKeyReleased(key)) return true;
        return false;
    }

    public boolean isAtLeastOneKeyReleased(Collection<Key> keys){
        for (Key key : keys) if (isKeyReleased(key)) return true;
        return false;
    }
}
