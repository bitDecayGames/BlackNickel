package com.bitdecay.blacknickel.editor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.blacknickel.component.IconComponent;
import com.bitdecay.blacknickel.component.SizeComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.gameobject.MyGameObjectFactory;
import com.bitdecay.jump.BitBody;
import com.bitdecay.jump.annotation.CantInspect;
import com.bitdecay.jump.gdx.level.RenderableLevelObject;
import com.bitdecay.jump.geom.BitRectangle;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This is a generic implementation of RenderableLevelObject.  It gets populated using the gobs.conf file.
 */
public class NewRoomLevelObject extends ConfBasedLevelObject {

    public static final String NAME = "Door";

    public String level = "";

    @JsonCreator
    public NewRoomLevelObject(){
        super(NAME);
    }

    @Override
    public RenderableLevelObject getNewCopy() throws Exception {
        return new NewRoomLevelObject();
    }
}
