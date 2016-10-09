package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This marks a component as ABLE to interact with a trigger
 */
public abstract class TriggererComponent extends AbstractComponent {

    public TriggererComponent(MyGameObject obj) {
        super(obj);
    }

    public TriggererComponent(MyGameObject obj, Config conf) { super(obj, conf); }
}
