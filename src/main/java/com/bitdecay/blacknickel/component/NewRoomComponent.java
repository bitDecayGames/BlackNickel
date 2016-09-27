package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This component is for getting to the next room
 */
public class NewRoomComponent extends AbstractComponent {

    private String level;

    public NewRoomComponent(MyGameObject obj, Config conf) {
        super(obj, conf);
        level = conf.getString("level");
    }

    public String level(){
        return level;
    }
}
