package com.bitdecay.blacknickel.component.trigger;

import com.bitdecay.blacknickel.component.AbstractComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This is the component that GETS triggered by the TriggerComponent
 */
public abstract class TriggerableComponent extends AbstractComponent {

    public TriggerableComponent(MyGameObject obj) {
        super(obj);
    }

    public abstract void execute(TriggerComponent origin);
}
