package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This component is the trigger responsible for adding the new room component
 */
public class NewRoomTriggerableComponent extends TriggerableComponent {

    private String level;

    public NewRoomTriggerableComponent(MyGameObject obj, Config conf) {
        super(obj, conf);
        level = conf.getString("level");
    }

    public NewRoomTriggerableComponent(MyGameObject obj, String level){
        super(obj);
        this.level = level;
    }

    public String level(){ return level; }

    @Override
    public void execute(TriggerComponent origin) {
        obj.addComponent(new NewRoomComponent(obj, level));
    }
}
