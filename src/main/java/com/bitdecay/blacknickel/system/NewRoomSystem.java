package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.component.EditableDoorComponent;
import com.bitdecay.blacknickel.component.NewRoomComponent;
import com.bitdecay.blacknickel.component.TextComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.room.GenericRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractSystem;
import com.bitdecay.jump.level.FileUtils;
import com.bitdecay.jump.level.Level;

/**
 * This system is in charge of setting up the new room when a NewRoomComponent is added to a gob
 */
public class NewRoomSystem extends AbstractSystem {

    public NewRoomSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        // if a new level object doesn't have a level associated, then it should be called out as an error
        gob.forEach(EditableDoorComponent.class, room -> {
            if (! gob.hasComponent(TextComponent.class)) {
                if (room.level == null || room.level.isEmpty())
                    new TextComponent(gob, "missing\nlevel", Color.RED.cpy(), 1f, new Vector2(0, 20)).addSelfToGameObject();
                else if (! Gdx.files.classpath("level/" + room.level + ".level").exists() && ! Gdx.files.classpath("level/" + room.level).exists())
                    new TextComponent(gob, "level\ndoesn't\nexist", Color.RED.cpy(), 1f, new Vector2(0, 30)).addSelfToGameObject();
            }
        });
        gob.forEach(NewRoomComponent.class, newRoomComponent -> {
            try {
                FileHandle f = Gdx.files.classpath("level/" + newRoomComponent.level() + ".level");
                if (! f.exists()) f = Gdx.files.classpath("level/" + newRoomComponent.level());
                Level level = FileUtils.loadFileAs(Level.class, f.readString());
                room.gameScreen().setRoom(new GenericRoom(level));
            } catch (Exception e){
                log.error("Could not set level to '" + newRoomComponent.level() + "'", e);
            }
        });
        return false;
    }
}
