package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.TimerComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of updating the timer components and calling their functions when the timer runs out.
 */
public class TimerSystem extends AbstractForEachUpdatableSystem {
    public TimerSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(TimerComponent.class) && ! gob.getComponent(TimerComponent.class).get().done;
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(TimerComponent.class, timer -> {
            timer.seconds -= delta;
            if (timer.seconds <= 0){
                timer.done = true;
                timer.execute();
            }
        });
    }

}
