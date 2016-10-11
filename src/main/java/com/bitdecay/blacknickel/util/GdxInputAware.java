package com.bitdecay.blacknickel.util;

import com.badlogic.gdx.Gdx;
import com.bitdecay.blacknickel.trait.IInputAware;

public class GdxInputAware implements IInputAware {

    @Override
    public boolean isKeyJustPressed(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }

    @Override
    public boolean isKeyPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }
}