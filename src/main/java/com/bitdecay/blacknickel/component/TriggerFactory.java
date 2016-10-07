package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import org.apache.log4j.Logger;

public class TriggerFactory {
    private static final Logger log = Logger.getLogger(TriggerFactory.class);

    private TriggerFactory(){}

    public static void setupTrigger(MyGameObject source, MyGameObject target){
        // TODO: need to take a source obj and a target obj and create both triggers and triggerables
        target.getComponent(TriggerableComponent.class).ifPresent(triggerableComponent -> {
            source.forEach(TriggerTypeComponent.class, triggerTypeComponent -> {
                switch (triggerTypeComponent.type().toLowerCase()){
                    case "single":
                        log.debug("Created single trigger");
                        new SingleUseTriggerComponent(source, triggerableComponent).addSelfToGameObject();
                        break;
                    case "unlimited":
                        log.debug("Created unlimited trigger");
                        new UnlimitedUseTriggerComponent(source, triggerableComponent).addSelfToGameObject();
                        break;
                    default:
                        log.debug("Default trigger type reached");
                        break;
                }
            });
        });
    }
}
