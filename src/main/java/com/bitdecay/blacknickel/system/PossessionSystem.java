package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.component.PossessableComponent;
import com.bitdecay.blacknickel.component.UnderControlComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
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
        gobs.stream().filter(a -> a.hasComponents(UnderControlComponent.class, PositionComponent.class)).collect(Collectors.toList()).forEach(possessor -> {
            if (Keyboard.isAtLeastOneKeyJustPressed(possessionButtons)) {
                room.sloMo();
            } else if (Keyboard.isAtLeastOneKeyPressed(possessionButtons)){
                // holding the key
            } else if (Keyboard.isAtLeastOneKeyJustReleased(possessionButtons)){
                PositionComponent possessorPos = possessor.getComponent(PositionComponent.class).get();
                gobs.stream().filter(b -> b.hasComponents(PossessableComponent.class, PositionComponent.class) && possessor != b).sorted((a, b) -> distance(possessorPos, a.getComponent(PositionComponent.class).get(), b.getComponent(PositionComponent.class).get())).findFirst().ifPresent(possessable -> {
                    possessor.removeComponent(UnderControlComponent.class);
                    possessable.addComponent(UnderControlComponent.class);

                    // TODO: I'd like to try and add a particle emitter on some sort of bullet thing here to show where you are possessing
                });
                room.realtime();
            }
        });
    }

    private int distance(PositionComponent orig, PositionComponent a, PositionComponent b){
        float aDst = Vector2.dst(orig.x, orig.y, a.x, a.y);
        float bDst = Vector2.dst(orig.x, orig.y, b.x, b.y);

        return aDst > bDst ? 1 : aDst < bDst ? -1 : 0;
    }
}
