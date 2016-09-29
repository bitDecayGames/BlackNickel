package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.Input;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.NewRoomComponent;
import com.bitdecay.blacknickel.component.NewRoomTriggerableComponent;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.component.SizeComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.room.GenericRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.blacknickel.util.InputHelper;
import com.bitdecay.jump.level.Level;
import com.bitdecay.jump.leveleditor.utils.LevelUtilities;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This system is in charge of updating the position and size component with data from the physics component
 */
public class NewRoomSystem extends AbstractUpdatableSystem {

    private List<Integer> interactButtons = Launcher.conf.getConfig("controls").getConfig("keyboard").getStringList("action").stream().map(Input.Keys::valueOf).filter(i -> i >= 0).collect(Collectors.toList());

    public NewRoomSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return checkForDoor(gob) || checkForTriggerable(gob);
    }

    @Override
    public void update(float delta) {
        gobs.forEach(a -> {
            if (checkForDoor(a)){
                gobs.forEach(b -> {
                    if (checkForTriggerable(b) && overlap(a, b) && InputHelper.isKeyJustPressed(interactButtons)){
                        a.forEach(NewRoomComponent.class, c -> {
                            try {
                                Level level = LevelUtilities.loadLevel("src/main/resources/level/" + c.level() + ".level");
                                room.gameScreen().setRoom(new GenericRoom(level));
                            } catch (Exception e){
                                System.err.println("Could not set level to '" + c.level() + "'");
                            }
                        });
                    }
                });
            }
        });
    }

    private boolean checkForDoor(MyGameObject gob){
        return gob.hasComponents(NewRoomComponent.class, SizeComponent.class, PositionComponent.class);
    }

    private boolean checkForTriggerable(MyGameObject gob){
        return gob.hasComponents(NewRoomTriggerableComponent.class, SizeComponent.class, PositionComponent.class);
    }

    private boolean overlap(MyGameObject a, MyGameObject b){
        return a.getComponent(SizeComponent.class).flatMap(sizeA -> a.getComponent(PositionComponent.class).flatMap(posA -> b.getComponent(SizeComponent.class).flatMap(sizeB -> b.getComponent(PositionComponent.class).map(posB -> {
            float aTop = posA.y + sizeA.h;
            float aBot = posA.y;
            float aLeft = posA.x;
            float aRight = posA.x + sizeA.w;

            float bTop = posB.y + sizeB.h;
            float bBot = posB.y;
            float bLeft = posB.x;
            float bRight = posB.x + sizeB.w;

            return aLeft < bRight && aRight > bLeft && aTop > bBot && aBot < bTop;
        })))).orElse(false);
    }
}
