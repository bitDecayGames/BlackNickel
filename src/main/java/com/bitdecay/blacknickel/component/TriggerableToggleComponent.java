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

    @Override
    public final void execute(TriggererComponent source, TriggerComponent origin){
        if (on) turnOff(source, origin);
        else turnOn(source, origin);
        on = !on;
    }

    public abstract void turnOn(TriggererComponent source, TriggerComponent origin);

    public abstract void turnOff(TriggererComponent source, TriggerComponent origin);
}
