package com.bitdecay.blacknickel.system.abstracted;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;

/**
 * All this class does is add one more layer for systems that don't need to have access to the full list of gobs when they are doing their processing.  In most cases, you will extend this class instead of AbstractSystem directly.  See the DrawSystem for an example of when you DO extend AbstractSystem directly.
 */
public abstract class AbstractForEachUpdatableSystem extends AbstractUpdatableSystem {

    public AbstractForEachUpdatableSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    public void update(float delta) {
        gobs.forEach(gob -> forEach(delta, gob));
    }

    protected abstract void forEach(float delta, MyGameObject gob);
}
