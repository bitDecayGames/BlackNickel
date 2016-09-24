package com.bitdecay.blacknickel.trait;

/**
 * The cleanup method gets called after the update and draw cycle.  It is used for removing/adding to lists.
 */
public interface ICleanup {
    boolean isDirty();
    void cleanup();
}
