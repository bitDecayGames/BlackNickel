package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.OverlapTriggerComponent;
import com.bitdecay.blacknickel.component.OverlapTriggererComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.gameobject.MyGameObjectUtils;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractUpdatableSystem;

/**
 * This system is responsible for executing triggers when they are overlapped by a matching object
 */
public class OverlapTriggerSystem extends AbstractUpdatableSystem {
    public OverlapTriggerSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(OverlapTriggerComponent.class) || gob.hasComponent(OverlapTriggererComponent.class);
    }

    @Override
    public void update(float delta) {
        gobs.forEach(a -> gobs.forEach(b -> {
            if (a != b && a.hasComponent(OverlapTriggerComponent.class) && b.hasComponent(OverlapTriggererComponent.class) && MyGameObjectUtils.overlap(a, b)) a.forEach(OverlapTriggerComponent.class, OverlapTriggerComponent::execute);
        }));
    }
}
