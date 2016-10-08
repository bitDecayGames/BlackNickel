package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component marks an object as able to trigger a trigger when it overlaps and activates it
 */
public abstract class ActivationTriggererComponent extends AbstractComponent {

    public ActivationTriggererComponent(MyGameObject obj) {
        super(obj);
    }
}
