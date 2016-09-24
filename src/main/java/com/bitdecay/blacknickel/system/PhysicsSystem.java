package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.PhysicsComponent;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of updating the position and size component with data from the physics component
 */
public class PhysicsSystem extends AbstractForEachUpdatableSystem {
    public PhysicsSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PhysicsComponent.class, PositionComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(PhysicsComponent.class, phy ->
            gob.forEach(PositionComponent.class, pos -> {
                pos.x = phy.body().aabb.xy.x;
                pos.y = phy.body().aabb.xy.y;
            })
        );
    }

}
