package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.TriggerComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractSystem;

/**
 * This system is responsible for triggering and hooking up triggers to triggerees
 */
public class TriggerSystem extends AbstractSystem {
    public TriggerSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        // logic goes here because this method is only called when gobs changes
        gob.forEach(TriggerComponent.class, trigger -> {
            trigger.execute();
            gob.removeComponent(trigger);
        });
        return false;
    }
}
