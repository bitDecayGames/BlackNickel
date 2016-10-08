package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This component extends the drawable component and it only draws a single static image.
 */
public class StaticImageComponent extends DrawableComponent {

    private TextureRegion image;
    private String path;

    public StaticImageComponent(MyGameObject obj, Config conf) {
        super(obj);
        path = conf.getString("path");
        image = MyGame.ATLAS.findRegion(path);
    }

    public StaticImageComponent(MyGameObject obj, String path){
        super(obj);
        this.path = path;
        image = MyGame.ATLAS.findRegion(path);
    }

    public String path(){ return path; }

    @Override
    public TextureRegion image() {
        return image;
    }
}
