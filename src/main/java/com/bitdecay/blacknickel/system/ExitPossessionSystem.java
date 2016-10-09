package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.Input;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.*;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.blacknickel.util.InputHelper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This system facilitates the exit of a possessor from a possessed object
 */
public class ExitPossessionSystem extends AbstractUpdatableSystem {

    private List<Integer> exitButtons = Launcher.conf.getConfig("controls").getConfig("keyboard").getStringList("action").stream().map(Input.Keys::valueOf).filter(i -> i >= 0).collect(Collectors.toList());

    public ExitPossessionSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(UnderControlComponent.class, PossessableComponent.class, PhysicsComponent.class);
    }

    @Override
    public void update(float delta) {
        if (InputHelper.isKeyJustPressed(exitButtons)) {
            gobs.forEach(possessed -> {
                possessed.forEach(PossessableComponent.class, possessableComponent -> {
                    if (possessableComponent.possessor != null){
                        possessed.forEach(PositionComponent.class, possessedPos -> {
                            possessableComponent.possessor.forEach(PositionComponent.class, possessorPos -> possessorPos.setPosition(possessedPos.x, possessedPos.y + 5));
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
}
