package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.trait.IExecutable;

/**
 * This is the component that DOES the triggering of the TriggerableComponent
 */
public abstract class TriggerComponent extends AbstractComponent implements IExecutable {

    private TriggerableComponent triggerable;

    public TriggerComponent(MyGameObject obj, TriggerableComponent triggerable) {
        super(obj);
        this.triggerable = triggerable;
    }

    @Override
    public void execute() {
        triggerable.execute(this);
    }
}
