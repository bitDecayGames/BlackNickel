package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component is for extending other drawable component types.  Ie: animated, static, etc
 */
public abstract class DrawableComponent extends AbstractComponent {

    public DrawableComponent(MyGameObject obj) {
        super(obj);
    }

    public abstract TextureRegion image();
}
