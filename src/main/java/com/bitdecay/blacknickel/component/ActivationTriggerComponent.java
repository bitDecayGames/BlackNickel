package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component allows for a single use trigger that triggers when there is overlap and a button click
 */
public class ActivationTriggerComponent extends TriggerComponent {

    public ActivationTriggerComponent(MyGameObject obj, TriggerableComponent triggerable) {
        super(obj, triggerable);
    }
    public ActivationTriggerComponent(MyGameObject obj, TriggerableComponent triggerable, int uses) {
        super(obj, triggerable, uses);
    }
}
