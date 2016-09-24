package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This component is the flag for being able to move a body around freely in the world (no gravity)
 */
public class FreestyleMovementComponent extends AbstractComponent {

    public float speed = 1;
    public float friction = 0.9f;

    public FreestyleMovementComponent(MyGameObject obj, Config conf) {
        super(obj, conf);
        speed = (float) conf.getDouble("speed");
        friction = (float) conf.getDouble("friction");
    }
}
