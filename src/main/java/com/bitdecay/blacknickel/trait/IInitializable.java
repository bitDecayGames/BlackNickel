package com.bitdecay.blacknickel.trait;

import com.bitdecay.blacknickel.room.AbstractRoom;

/**
 * Allows specific components that require initialization to be handled in a ECS way
 */
public interface IInitializable {
    boolean isInitialized();
    void initialize(AbstractRoom room);
}
