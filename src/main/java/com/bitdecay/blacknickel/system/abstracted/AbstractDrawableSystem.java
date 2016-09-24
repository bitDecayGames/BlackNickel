package com.bitdecay.blacknickel.system.abstracted;

import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.trait.IDrawWithCamera;

/**
 * A drawable system
 */
public abstract class AbstractDrawableSystem extends AbstractSystem implements IDrawWithCamera {

    public AbstractDrawableSystem(AbstractRoom room) {
        super(room);
    }
}
