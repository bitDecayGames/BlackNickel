package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component allows for a single use trigger that triggers on an overlap
 */
public class OverlapTriggerComponent extends TriggerComponent {

    public OverlapTriggerComponent(MyGameObject obj, TriggerableComponent triggerable) {
        super(obj, triggerable);
    }

    public OverlapTriggerComponent(MyGameObject obj, TriggerableComponent triggerable, int uses) {
        super(obj, triggerable, uses);
    }
}
