package com.bitdecay.blacknickel.trait;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Allows object to draw itself using SpriteBatch and a camera
 */
public interface IDrawWithCamera {
    void draw(SpriteBatch spriteBatch, OrthographicCamera camera);
}
