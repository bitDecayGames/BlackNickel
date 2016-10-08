package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component marks an object as able to trigger a trigger when it overlaps it
 */
public class OverlapTriggererComponent extends AbstractComponent {

    public OverlapTriggererComponent(MyGameObject obj) {
        super(obj);
    }
}
