package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.PhysicsComponent;
import com.bitdecay.blacknickel.component.ShellMovementComponent;
import com.bitdecay.blacknickel.component.UnderControlComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;
import com.bytebreakstudios.input.Key;
import com.bytebreakstudios.input.Keyboard;

/**
 * This system is in charge of updating the position and size component with data from the physics component
 */
public class ShellMovementSystem extends AbstractForEachUpdatableSystem {

    public ShellMovementSystem(AbstractRoom room) { super(room); }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(ShellMovementComponent.class, PhysicsComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(ShellMovementComponent.class, smc -> {
            if (smc.controllable && gob.hasComponent(UnderControlComponent.class)){
                if (Keyboard.isKeyJustPressed(Key.A)){
                    smc.moving = true;
                    smc.direction = -1; // TODO: this is backwards because of line 39
                } else if (Keyboard.isKeyJustPressed(Key.D)){
                    smc.moving = true;
                    smc.direction = 1; // TODO: same issue as above
                }
            }
            if (smc.moving) {
                gob.forEach(PhysicsComponent.class, phy -> {
                    if (smc.direction == 0) smc.direction = 1;
                    if (phy.body().velocity.x == 0){
                        smc.direction *= -1;
                    }
                    phy.body().velocity.x = smc.direction * smc.speed;
                });
            }
        });
    }
}
