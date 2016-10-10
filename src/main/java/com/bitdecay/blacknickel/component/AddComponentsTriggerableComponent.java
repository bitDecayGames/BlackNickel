package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.gameobject.MyGameObjectFromConf;
import com.typesafe.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * This component is the trigger responsible for adding a list of components to an object
 */
public class AddComponentsTriggerableComponent extends TriggerableComponent {

    private List<AbstractComponent> components = new ArrayList<>();

    public AddComponentsTriggerableComponent(MyGameObject obj, Config conf) {
        super(obj, conf);
        conf.getConfigList("components").forEach(compConf -> components.add(MyGameObjectFromConf.componentFromConf(obj, compConf)));
    }

    @Override
    public void execute(TriggererComponent source, TriggerComponent origin) {
        components.forEach(obj::addComponent);
    }
}
