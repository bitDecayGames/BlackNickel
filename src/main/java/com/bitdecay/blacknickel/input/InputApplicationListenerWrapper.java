package com.bitdecay.blacknickel.input;

import com.badlogic.gdx.ApplicationListener;

/**
 * The purpose of this class is to provide an easy way to add keyboard and mouse update calls without requiring it of the end user
 */
public class InputApplicationListenerWrapper implements ApplicationListener {

    private ApplicationListener core;

    public InputApplicationListenerWrapper(ApplicationListener core) {
        this.core = core;
    }

    @Override
    public void create() {
        core.create();
    }

    @Override
    public void resize(int i, int i1) {
        core.resize(i, i1);
    }

    @Override
    public void render() {
        Keyboard.step();
        Mouse.step();
        core.render();
    }

    @Override
    public void pause() {
        core.pause();
    }

    @Override
    public void resume() {
        core.resume();
    }

    @Override
    public void dispose() {
        core.dispose();
    }
}
