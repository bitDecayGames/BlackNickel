package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;
import org.apache.log4j.Logger;

/**
 * All components should extend this class
 */
public abstract class AbstractComponent {
    protected final Logger log;
    protected final MyGameObject obj;
    public AbstractComponent(MyGameObject obj){
        this.log = Logger.getLogger(this.getClass());
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
