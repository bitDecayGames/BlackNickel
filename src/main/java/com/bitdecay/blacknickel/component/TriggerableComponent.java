package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This is the component that GETS triggered by the TriggerComponent
 */
public abstract class TriggerableComponent extends AbstractComponent {

    public TriggerableComponent(MyGameObject obj) {
        super(obj);
    }

    public TriggerableComponent(MyGameObject obj, Config conf) { super(obj, conf); }

    public abstract void execute(TriggerComponent origin);
}
