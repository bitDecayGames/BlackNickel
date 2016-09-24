package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.CameraFollowComponent;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of updating the camera with the list of points to follow each step.
 */
public class CameraUpdateSystem extends AbstractForEachUpdatableSystem {
    public CameraUpdateSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(CameraFollowComponent.class, PositionComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(PositionComponent.class, pos -> room.camera.addFollowPoint(pos.toVector2()));
    }

}
