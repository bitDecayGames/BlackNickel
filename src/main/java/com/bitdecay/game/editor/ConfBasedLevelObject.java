package com.bitdecay.game.editor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.component.IconComponent;
import com.bitdecay.game.component.SizeComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.jump.BitBody;
import com.bitdecay.jump.annotation.CantInspect;
import com.bitdecay.jump.gdx.level.RenderableLevelObject;
import com.bitdecay.jump.geom.BitRectangle;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfBasedLevelObject extends RenderableLevelObject {

    @CantInspect
    private final String name;

    @CantInspect
    private final MyGameObject obj;

    public ConfBasedLevelObject(){
        throw new RuntimeException("If this constructor gets called, then there is no way to figure out what game object this level object refers to.");
    }

    @JsonCreator
    public ConfBasedLevelObject(@JsonProperty("name") String name){
        super();
        this.name = name;
        this.rect = new BitRectangle(0, 0, 20, 20);

        obj = MyGameObjectFactory.objectFromConf(null, name, 0, 0);

        rect = new BitRectangle(0, 0, 16, 16); // default values in case there is no size
        obj.getComponent(SizeComponent.class).ifPresent(size -> {
            rect.width = size.w;
            rect.height = size.h;
        });
    }

    @Override
    public TextureRegion texture() {
        return obj.getComponent(IconComponent.class).map(IconComponent::icon).orElseGet(null);
    }

    @Override
    public BitBody buildBody() {
        return null; // I guess this stays null?
    }

    @Override
    public String name() {
        return name;
    }
}
