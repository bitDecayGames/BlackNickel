package com.bitdecay.blacknickel.system.abstracted;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;

/**
 * All this class does is add one more layer for systems that don't need to have access to the full list of gobs when they are doing their processing.  In most cases, you will extend this class instead of AbstractSystem directly.  See the DrawSystem for an example of when you DO extend AbstractSystem directly.
 */
public abstract class AbstractForEachDrawableSystem extends AbstractDrawableSystem {

    public AbstractForEachDrawableSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        gobs.forEach(gob -> forEach(spriteBatch, camera, gob));
    }

    protected abstract void forEach(SpriteBatch spriteBatch, OrthographicCamera camera, MyGameObject gob);
}
