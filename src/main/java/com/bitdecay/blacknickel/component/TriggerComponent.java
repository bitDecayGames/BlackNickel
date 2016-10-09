package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This is the component that DOES the triggering of the TriggerableComponent
 */
public abstract class TriggerComponent extends AbstractComponent {

    protected int uses = -1;
    protected TriggerableComponent triggerable;

    public TriggerComponent(MyGameObject obj, TriggerableComponent triggerable) {
        super(obj);
        this.triggerable = triggerable;
    }

    public TriggerComponent(MyGameObject obj, TriggerableComponent triggerable, int uses){
        this(obj, triggerable);
        this.uses = uses;
    }

    public void execute(TriggererComponent source) {
        if (uses > 0 || uses < 0) {
            uses -= 1;
            triggerable.execute(source, this);
        }
    }
}
