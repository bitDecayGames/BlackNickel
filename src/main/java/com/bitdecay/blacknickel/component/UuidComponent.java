package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component is mostly for setting up triggers properly
 */
public class UuidComponent extends AbstractComponent {
    private String uuid;

    public UuidComponent(MyGameObject obj, String uuid){
        super(obj);
        this.uuid = uuid;
    }

    public String uuid() { return uuid; }

    @Override
    public String toString() {
        return uuid();
    }
}
