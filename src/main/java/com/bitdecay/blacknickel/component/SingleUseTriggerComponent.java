package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component allows for a single use trigger
 */
public class SingleUseTriggerComponent extends TriggerComponent {

    protected boolean used = false;

    public SingleUseTriggerComponent(MyGameObject obj, TriggerableComponent triggerable) {
        super(obj, triggerable);
    }

    public boolean used(){ return used; }

    @Override
    public void execute() {
        if (! used()) {
            used = true;
            super.execute();
        } else log.debug("Tried to execute a single use trigger more than once.");
    }
}
