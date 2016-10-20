package com.bitdecay.blacknickel.input;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public enum MouseButton {

    LEFT(0),
    RIGHT(1),
    MIDDLE(2),
    BACK(3),
    FORWARD(4);

    public int code;
    public List<String> aliases;
    MouseButton(int code, String... aliases){
        this.code = code;
        this.aliases = Collections.unmodifiableList(Arrays.asList(aliases));
    }

    public boolean matchesName(String name){
        return name().equalsIgnoreCase(name) || aliases.stream().anyMatch(alias -> alias.equalsIgnoreCase(name));
    }

    public static Optional<MouseButton> fromCode(int code){
        return stream().filter(btn -> btn.code == code).findFirst();
    }

    public static Optional<MouseButton> fromString(String name){
        return stream().filter(btn -> btn.matchesName(name)).findFirst();
    }

    public static List<MouseButton> buttons(){
        return Arrays.asList(MouseButton.values());
    }

    public static Stream<MouseButton> stream(){
        return Arrays.stream(MouseButton.values());
    }
}
