package com.bitdecay.blacknickel.trait;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Allows the object to draw a shape at a given location
 */
public interface IShapeDraw {
    void draw(ShapeRenderer shapeRenderer, Vector2 position);
}
