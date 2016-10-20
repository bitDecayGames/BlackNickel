package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.*;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.gameobject.MyGameObjectUtils;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractUpdatableSystem;
import com.bytebreakstudios.input.Key;
import com.bytebreakstudios.input.Keyboard;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This system facilitates possession of a possessable object by a possessor
 */
public class PossessionSystem extends AbstractUpdatableSystem {

    private List<Key> possessionButtons = Launcher.conf.getConfig("controls").getConfig("keyboard").getStringList("action").stream().map(Key::fromString).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

    public PossessionSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(UnderControlComponent.class) || gob.hasComponents(PossessableComponent.class);
    }

    @Override
    public void update(float delta) {
        gobs.stream().filter(a -> a.hasComponents(UnderControlComponent.class, PositionComponent.class)).forEach(possessor -> {
            if (Keyboard.isAtLeastOneKeyJustPressed(possessionButtons)) {
                room.sloMo();
                gobs.stream().filter(b -> b.hasComponents(PossessableComponent.class, PositionComponent.class, SizeComponent.class)).forEach(possessable -> {
                    if (MyGameObjectUtils.overlap(possessor, possessable)) {
                        possessor.removeComponent(UnderControlComponent.class);
                        possessor.addComponent(RemoveNowComponent.class);
                        possessable.addComponent(UnderControlComponent.class);
                        possessable.forEach(PossessableComponent.class, possessableComponent -> possessableComponent.possessor = possessor);
                    }
                });
            } else if (Keyboard.isAtLeastOneKeyJustReleased(possessionButtons)){
                room.realtime();
            }
        });
    }
}
