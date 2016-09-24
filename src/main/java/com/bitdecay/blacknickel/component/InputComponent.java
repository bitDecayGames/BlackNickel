package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.jump.control.ControlMap;

/**
 * This component is to add controls to a physical body in Jump
 */
public abstract class InputComponent extends AbstractComponent implements ControlMap {

    protected boolean enabled = true;

    public InputComponent(MyGameObject obj) {
        super(obj);
    }

    @Override
    public void enable() {
        enabled = true;
    }

    @Override
    public void disable() {
        enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
