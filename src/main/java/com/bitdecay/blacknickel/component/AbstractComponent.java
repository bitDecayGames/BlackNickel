package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * All components should extend this class
 */
public abstract class AbstractComponent {
    protected final MyGameObject obj;
    public AbstractComponent(MyGameObject obj){
        this.obj = obj;
    }

    public AbstractComponent(MyGameObject obj, Config conf){
        this(obj);
    }

    public AbstractComponent addSelfToGameObject(){
        obj.addComponent(this);
        return this;
    }

    @Override
    public String toString(){return this.getClass().getSimpleName();}
}
