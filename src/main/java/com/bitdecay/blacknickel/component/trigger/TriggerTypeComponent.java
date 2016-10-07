package com.bitdecay.blacknickel.component.trigger;

import com.bitdecay.blacknickel.component.AbstractComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This component is used for setting up triggers.  It defines what kind of trigger this game object can be.
 */
public class TriggerTypeComponent extends AbstractComponent {
    private String type;

    public TriggerTypeComponent(MyGameObject obj, String type){
        super(obj);
        this.type = type;
    }

    public TriggerTypeComponent(MyGameObject obj, Config conf){
        this(obj, conf.getString("type"));
    }

    @Override
    public String toString() {
        return this.type;
    }
}
