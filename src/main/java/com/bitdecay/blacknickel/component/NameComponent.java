package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component is mostly for the level editor for when you want to add a name to the level object.
 */
public class NameComponent extends AbstractComponent {
    private String name;

    public NameComponent(MyGameObject obj, String name){
        super(obj);
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
