package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.FreestyleMovementComponent;
import com.bitdecay.blacknickel.component.PhysicsComponent;
import com.bitdecay.blacknickel.component.UnderControlComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.jump.geom.BitPoint;
import com.bytebreakstudios.input.Key;
import com.bytebreakstudios.input.Keyboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This system is in charge of updating the position and size component with data from the physics component
 */
public class FreestyleMovementSystem extends AbstractForEachUpdatableSystem {

    private Map<String, List<Key>> controls = new HashMap<>();

    public FreestyleMovementSystem(AbstractRoom room) {
        super(room);
        controls.put("up", getControls("up"));
        controls.put("down", getControls("down"));
        controls.put("left", getControls("left"));
        controls.put("right", getControls("right"));
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(UnderControlComponent.class, FreestyleMovementComponent.class, PhysicsComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(FreestyleMovementComponent.class, fmc -> {
            BitPoint movement = new BitPoint();
            if (isPressed("up")) movement.add(0, 1);
            if (isPressed("down")) movement.add(0, -1);
            if (isPressed("right")) movement.add(1, 0);
            if (isPressed("left")) movement.add(-1, 0);
            gob.forEach(PhysicsComponent.class, phy -> {
                if (movement.len() > 0) {
                    BitPoint vel = new BitPoint(phy.body().velocity);
                    vel.add(movement.normalize().scale(fmc.speed / 10f));
                    if (vel.len() > fmc.speed) phy.body().velocity = vel.normalize().scale(fmc.speed);
                    else phy.body().velocity = vel;
                } else phy.body().velocity = phy.body().velocity.scale(fmc.friction);
            });
        });
    }

    private boolean isPressed(String name){
        return Keyboard.isAtLeastOneKeyPressed(controls.get(name));
    }

    private List<Key> getControls(String name){
        return Launcher.conf.getConfig("controls").getConfig("keyboard").getStringList(name).stream().map(Key::fromString).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

}
