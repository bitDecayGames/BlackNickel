package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.Input;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.*;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.gameobject.MyGameObjectUtils;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.blacknickel.util.InputHelper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This system facilitates possession of a possessable object by a possessor
 */
public class PossessionSystem extends AbstractUpdatableSystem {

    private List<Integer> possesionButtons = Launcher.conf.getConfig("controls").getConfig("keyboard").getStringList("action").stream().map(Input.Keys::valueOf).filter(i -> i >= 0).collect(Collectors.toList());

    public PossessionSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(UnderControlComponent.class, PossessorComponent.class, PhysicsComponent.class) || gob.hasComponents(PossessableComponent.class, PhysicsComponent.class);
    }

    @Override
    public void update(float delta) {
        if (InputHelper.isKeyJustPressed(possesionButtons)) {
            gobs.stream().filter(a -> a.hasComponents(UnderControlComponent.class, PossessorComponent.class, PositionComponent.class, SizeComponent.class)).forEach(possessor -> {
                gobs.stream().filter(b -> b.hasComponents(PossessableComponent.class, PositionComponent.class, SizeComponent.class)).forEach(possessable -> {
                    if (MyGameObjectUtils.overlap(possessor, possessable)) {
                        possessor.removeComponent(UnderControlComponent.class);
                        possessor.addComponent(RemoveNowComponent.class);
                        possessable.addComponent(UnderControlComponent.class);
                        possessable.forEach(PossessableComponent.class, possessableComponent -> possessableComponent.possessor = possessor);
                    }
                });
            });
        }
    }
}
