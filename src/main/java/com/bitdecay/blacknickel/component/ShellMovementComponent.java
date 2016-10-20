package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This component is the flag for moving like a koopa shell across the ground
 */
public class ShellMovementComponent extends AbstractComponent {

    public float speed = 0;
    public int direction = 0;
    public boolean moving = false;
    public boolean controllable = false;

    public ShellMovementComponent(MyGameObject obj, Config conf) {
        super(obj, conf);
        speed = (float) conf.getDouble("speed");
        moving = conf.getBoolean("moving");
        direction = conf.getInt("direction");
        if (conf.hasPath("controllable")) controllable = conf.getBoolean("controllable");
    }
}
