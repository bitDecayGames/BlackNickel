package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This is the component that GETS triggered by the TriggerComponent and then teleports the triggerer
 */
public class TeleportToHereTriggerableComponent extends TriggerableComponent {

    public TeleportToHereTriggerableComponent(MyGameObject obj) {
        super(obj);
    }

    public TeleportToHereTriggerableComponent(MyGameObject obj, Config conf) { super(obj, conf); }

    public void execute(TriggererComponent source, TriggerComponent origin){
        obj.forEach(PositionComponent.class, thisPos -> source.obj.forEach(PositionComponent.class, sourcePos -> {
            sourcePos.x = thisPos.x;
            sourcePos.y = thisPos.y;
            source.obj.forEach(PhysicsComponent.class, sourcePhy -> {
                sourcePhy.setPosition(sourcePos.x, sourcePos.y);
                sourcePhy.setVelocity(0, 0);
            });
        }));
    }
}
