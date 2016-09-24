package com.bitdecay.blacknickel.system.abstracted;

import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.trait.IUpdate;

/**
 * An updatable system
 */
public abstract class AbstractUpdatableSystem extends AbstractSystem implements IUpdate {

    public AbstractUpdatableSystem(AbstractRoom room) {
        super(room);
    }
}
