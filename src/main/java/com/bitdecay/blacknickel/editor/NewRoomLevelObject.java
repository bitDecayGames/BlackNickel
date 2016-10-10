package com.bitdecay.blacknickel.editor;

import com.bitdecay.jump.gdx.level.RenderableLevelObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is a generic implementation of RenderableLevelObject.  It gets populated using the gobs.conf file.
 */
public class NewRoomLevelObject extends ConfBasedLevelObject {

    public String level = "";

    @JsonCreator
    public NewRoomLevelObject(@JsonProperty("name") String name){
        super(name);
    }

    @Override
    public RenderableLevelObject getNewCopy() throws Exception {
        return new NewRoomLevelObject(name());
    }
}
