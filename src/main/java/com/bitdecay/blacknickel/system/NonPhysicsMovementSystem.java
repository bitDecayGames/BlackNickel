package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.AccelerationComponent;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.component.VelocityComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of changing the position and velocity values of NON-PHYSICS objects
 */
public class NonPhysicsMovementSystem extends AbstractForEachUpdatableSystem {

    public NonPhysicsMovementSystem(AbstractRoom room) { super(room); }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(PositionComponent.class) && gob.hasComponent(VelocityComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(PositionComponent.class, pos -> gob.forEach(VelocityComponent.class, velocity -> {
            pos.x += velocity.x;
            pos.y += velocity.y;
            gob.forEach(AccelerationComponent.class, acceleration -> {
                velocity.x += acceleration.x;
                velocity.y += acceleration.y;
            });
        }));
    }

}
