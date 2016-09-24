package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractDrawableSystem;
import com.bitdecay.blacknickel.trait.IShapeDraw;

/**
 * This system is in charge of providing a position to the IShapeDrawComponents so that they are able to correctly draw themselves.
 */
public class ShapeDrawSystem extends AbstractDrawableSystem {
    public ShapeDrawSystem(AbstractRoom room) {
        super(room);
        room.shapeRenderer.setAutoShapeType(true);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(IShapeDraw.class, PositionComponent.class);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        room.shapeRenderer.setProjectionMatrix(camera.combined);
        room.shapeRenderer.begin();
        gobs.forEach(gob ->
                gob.forEach(IShapeDraw.class, drawable ->
                        gob.forEach(PositionComponent.class, positionComponent ->
                                drawable.draw(room.shapeRenderer, positionComponent.toVector2()))));
        room.shapeRenderer.end();
    }
}
