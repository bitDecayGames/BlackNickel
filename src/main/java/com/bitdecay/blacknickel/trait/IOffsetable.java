package com.bitdecay.blacknickel.trait;

import com.badlogic.gdx.math.Vector2;

/**
 * Allows a component to have an offset from the position (like when drawing debug text)
 */
public interface IOffsetable {
    Vector2 getOffset();
}
