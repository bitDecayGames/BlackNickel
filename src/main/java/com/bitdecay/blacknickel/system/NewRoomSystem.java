package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.*;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.gameobject.MyGameObjectUtils;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.room.GenericRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.blacknickel.util.InputHelper;
import com.bitdecay.jump.level.FileUtils;
import com.bitdecay.jump.level.Level;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This system is in charge of setting up the new room when a NewRoomComponent is interacted with
 */
public class NewRoomSystem extends AbstractUpdatableSystem {

    private List<Integer> interactButtons = Launcher.conf.getConfig("controls").getConfig("keyboard").getStringList("action").stream().map(Input.Keys::valueOf).filter(i -> i >= 0).collect(Collectors.toList());

    public NewRoomSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        // if a new level object doesn't have a level associated, then it should be called out as an error
        gob.forEach(NewRoomComponent.class, room -> {
            if (! gob.hasComponent(TextComponent.class)) {
                if (room.level() == null || room.level().isEmpty())
                    new TextComponent(gob, "missing\nlevel", Color.RED.cpy(), 1f, new Vector2(0, 20)).addSelfToGameObject();
                else if (! Gdx.files.classpath("level/" + room.level() + ".level").exists())
                    new TextComponent(gob, "level\ndoesn't\nexist", Color.RED.cpy(), 1f, new Vector2(0, 30)).addSelfToGameObject();
            }
        });
        return checkForDoor(gob) || checkForTriggerable(gob);
    }

    @Override
    public void update(float delta) {
        gobs.forEach(a -> {
            if (checkForDoor(a)){
                gobs.forEach(b -> {
                    if (checkForTriggerable(b) && MyGameObjectUtils.overlap(a, b) && InputHelper.isKeyJustPressed(interactButtons)){
                        a.forEach(NewRoomComponent.class, c -> {
                            try {
                                Level level = FileUtils.loadFileAs(Level.class, Gdx.files.classpath("level/" + c.level() + ".level").readString());
                                room.gameScreen().setRoom(new GenericRoom(level));
                            } catch (Exception e){
                                log.error("Could not set level to '" + c.level() + "'");
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
}
