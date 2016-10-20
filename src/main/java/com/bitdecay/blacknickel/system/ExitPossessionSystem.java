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
 * This system facilitates the exit of a possessor from a possessed object
 */
public class ExitPossessionSystem extends AbstractUpdatableSystem {

    private List<Key> exitButtons = Launcher.conf.getConfig("controls").getConfig("keyboard").getStringList("action").stream().map(Key::fromString).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

    private final static int EXIT_Y_POS_DELTA = 5;

    public ExitPossessionSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(UnderControlComponent.class, PossessableComponent.class, PhysicsComponent.class);
    }

    @Override
    public void update(float delta) {
        if (Keyboard.isAtLeastOneKeyJustPressed(exitButtons)) {
            gobs.forEach(possessed -> {
                possessed.forEach(PossessableComponent.class, possessableComponent -> {
                    if (possessableComponent.possessor != null && hasSpaceToExit(possessed.getComponent(PositionComponent.class), possessableComponent.possessor.getComponent(SizeComponent.class))) {
                        possessed.forEach(PositionComponent.class, possessedPos -> {
                            possessableComponent.possessor.forEach(PositionComponent.class, possessorPos -> possessorPos.setPosition(possessedPos.x, possessedPos.y + EXIT_Y_POS_DELTA));
                            possessableComponent.possessor.forEach(PhysicsComponent.class, possessorPhy -> possessorPhy.setVelocity(0, 0));
                        });
                        possessableComponent.possessor.removeComponent(RemoveNowComponent.class);
                        new TimerComponent(possessableComponent.possessor, 0.1f, (possessor, timer) -> {
                            possessor.addComponent(UnderControlComponent.class);
                            possessor.removeComponent(timer);
                        }).addSelfToGameObject();
                        room.getGameObjects().add(possessableComponent.possessor);

                        possessed.removeComponent(UnderControlComponent.class);
                        possessableComponent.possessor = null;
                    }
                });
            });
        }
    }

    private boolean hasSpaceToExit(Optional<PositionComponent> optPossessedPos, Optional<SizeComponent> optPossessorSize){
        if ( ! optPossessedPos.isPresent() || ! optPossessorSize.isPresent()) return false;
        else {
            PositionComponent pos = new PositionComponent(null, optPossessedPos.get().x, optPossessedPos.get().y + EXIT_Y_POS_DELTA);
            SizeComponent size = optPossessorSize.get();

            return room.getGameObjects().filter(tile -> {
                // currently only checks against tiles, not moving platforms or other objects
                if (tile.hasComponents(TileComponent.class, PositionComponent.class, SizeComponent.class)){
                    PositionComponent tilePos = tile.getComponent(PositionComponent.class).get();
                    SizeComponent tileSize = tile.getComponent(SizeComponent.class).get();
                    return tilePos.toVector2().dst(pos.x, pos.y) < size.w * 5 && MyGameObjectUtils.overlap(pos, size, tilePos, tileSize);
                } else return false;
            }).size() == 0;
        }
    }
}
