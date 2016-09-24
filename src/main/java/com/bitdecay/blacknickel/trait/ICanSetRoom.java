package com.bitdecay.blacknickel.trait;

import com.bitdecay.blacknickel.room.AbstractRoom;

/**
 * Allow the object to set the current room
 */
public interface ICanSetRoom {
    void setRoom(AbstractRoom room);
}
