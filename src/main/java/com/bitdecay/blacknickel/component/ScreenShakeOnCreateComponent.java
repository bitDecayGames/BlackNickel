package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This component causes the screen to shake when it is initially added to an object
 */
public class ScreenShakeOnCreateComponent extends AbstractComponent {
    private float duration = 0;

    public ScreenShakeOnCreateComponent(MyGameObject obj, Config conf){
        super(obj, conf);
        duration = (float) conf.getDouble("duration");
    }

    public float duration(){ return duration; }
}
