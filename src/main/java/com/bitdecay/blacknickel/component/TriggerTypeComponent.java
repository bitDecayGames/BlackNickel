package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This component is used for setting up triggers.  It defines what kind of trigger this game object can be.
 */
public class TriggerTypeComponent extends AbstractComponent {
    private String type;
    private int uses = -1;

    public TriggerTypeComponent(MyGameObject obj, String type, int uses){
        super(obj);
        this.type = type;
        this.uses = uses;
    }

    public TriggerTypeComponent(MyGameObject obj, String type){
        this(obj, type, -1);
    }

    public TriggerTypeComponent(MyGameObject obj, Config conf){
        this(obj, conf.getString("type"), conf.hasPath("uses") ? conf.getInt("uses") : -1);
    }

    public String type(){ return type; }

    public int uses(){ return uses; }

    @Override
    public String toString() {
        return this.type;
    }
}
