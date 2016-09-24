package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * Currently this is not used for anything.
 */
public class SizeComponent extends AbstractComponent {
    public float w = 0;
    public float h = 0;

    public SizeComponent(MyGameObject obj, Config conf) {
        this(obj, (float) conf.getDouble("w"), (float) conf.getDouble("h"));
    }

    public SizeComponent(MyGameObject obj, float width, float height){
        super(obj);
        this.w = width;
        this.h = height;
    }

    public void set(float width, float height) {
        w = width;
        h = height;
    }
}
