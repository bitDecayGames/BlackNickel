package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This is the component that GETS triggered by the TriggerComponent and will toggle back and forth
 */
public abstract class TriggerableToggleComponent extends TriggerableComponent {

    private boolean on = false;

    public TriggerableToggleComponent(MyGameObject obj) {
        super(obj);
    }

    public TriggerableToggleComponent(MyGameObject obj, Config conf) { super(obj, conf); }

    public final void execute(TriggerComponent origin){
        if (on) turnOff(origin);
        else turnOn(origin);
        on = !on;
    }

    public abstract void turnOn(TriggerComponent origin);

    public abstract void turnOff(TriggerComponent origin);
}
