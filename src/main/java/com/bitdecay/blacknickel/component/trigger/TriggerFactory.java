package com.bitdecay.blacknickel.component.trigger;

import com.bitdecay.blacknickel.component.NameComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import org.apache.log4j.Logger;

public class TriggerFactory {
    private static final Logger log = Logger.getLogger(TriggerFactory.class);

    private TriggerFactory(){}

    public static void setupTrigger(MyGameObject source, MyGameObject target){
        // TODO: need to take a source obj and a target obj and create both triggers and triggerables
        source.forEach(NameComponent.class, sourceName -> {
            target.forEach(NameComponent.class, targetName -> {
                log.debug("Setup trigger from " + sourceName + " to " + targetName);
            });
        });
    }
}
