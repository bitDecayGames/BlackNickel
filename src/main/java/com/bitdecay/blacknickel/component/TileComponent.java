package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This calls out that an object is a tile
 */
public class TileComponent extends DrawableComponent {
    private TextureRegion image;
    public TileComponent(MyGameObject obj, TextureRegion image) {
        super(obj);
        this.image = image;
    }

    @Override
    public TextureRegion image() {
        return image;
    }
}
