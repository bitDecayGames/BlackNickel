package com.bitdecay.blacknickel.trait;

/**
 * Called to dispose of disposable objects in libgdx like SpriteBatch or Texture (or BitmapFont... Jake...)
 */
public interface IDisposable {
    void dispose();
}
