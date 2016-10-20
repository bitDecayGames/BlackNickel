package com.bitdecay.blacknickel.input;

import com.badlogic.gdx.Gdx;

public class GdxInputAware implements IInputAware {

    @Override
    public boolean isKeyJustPressed(Key key) { return Gdx.input.isKeyJustPressed(key.code); }

    @Override
    public boolean isKeyPressed(Key key) {
        return Gdx.input.isKeyPressed(key.code);
    }
}