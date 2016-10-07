package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component allows for an unlimited use trigger
 */
public class UnlimitedUseTriggerComponent extends TriggerComponent {

    public UnlimitedUseTriggerComponent(MyGameObject obj, TriggerableComponent triggerable) {
        super(obj, triggerable);
    }
}
