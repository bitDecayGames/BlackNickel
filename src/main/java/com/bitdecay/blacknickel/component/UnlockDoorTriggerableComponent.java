package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component is the trigger responsible for adding a list of components to an object
 */
public class UnlockDoorTriggerableComponent extends TriggerableSelfRemovingComponent {

    private NewRoomTriggerableComponent newRoom;

    public UnlockDoorTriggerableComponent(MyGameObject obj, NewRoomTriggerableComponent newRoom) {
        super(obj);
        this.newRoom = newRoom;
    }

    @Override
    public void innerExecute(TriggererComponent source, TriggerComponent origin) {
        // adds the new room triggerable to this obj and sets up the trigger
        obj.addComponent(newRoom);
        obj.addComponent(new TimerComponent(obj, 0.1f, (gob, timer) -> {
            gob.removeComponent(timer);
            TriggerFactory.setupTrigger(gob, gob);
        }));
    }
}
