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
        // TODO: this logic needs to change.  I only want to execute the trigger when some condition is met (ie. stepping on it)
        gob.forEach(TriggerComponent.class, trigger -> {
            trigger.execute();
            gob.removeComponent(trigger);
        });
        return false;
    }
}
