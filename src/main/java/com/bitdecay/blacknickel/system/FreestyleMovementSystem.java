package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.FreestyleMovementComponent;
import com.bitdecay.blacknickel.component.PhysicsComponent;
import com.bitdecay.blacknickel.component.UnderControlComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.blacknickel.util.InputHelper;
import com.bitdecay.jump.geom.BitPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This system is in charge of updating the position and size component with data from the physics component
 */
public class FreestyleMovementSystem extends AbstractForEachUpdatableSystem {

    private Map<String, List<Integer>> controls = new HashMap<>();

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
            Vector2 movement = new Vector2();
            if (isPressed("up")) movement.add(0, 1);
            if (isPressed("down")) movement.add(0, -1);
            if (isPressed("right")) movement.add(1, 0);
            if (isPressed("left")) movement.add(-1, 0);
            gob.forEach(PhysicsComponent.class, phy -> {
                if (movement.len() > 0) {
                    movement.nor().scl(fmc.speed);
                    phy.body().velocity = new BitPoint(movement.x, movement.y);
                } else phy.body().velocity = phy.body().velocity.scale(fmc.friction);
            });
        });
    }

    private boolean isPressed(String name){
        return InputHelper.isKeyPressed(controls.get(name));
    }

    private List<Integer> getControls(String name){
        return Launcher.conf.getConfig("controls").getConfig("keyboard").getStringList(name).stream().map(Input.Keys::valueOf).filter(i -> i >= 0).collect(Collectors.toList());
    }

}
