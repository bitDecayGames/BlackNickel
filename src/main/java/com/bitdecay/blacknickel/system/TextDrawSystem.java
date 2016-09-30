package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.component.TextComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractDrawableSystem;

/**
 * This system is in charge of drawing text on the screen
 */
public class TextDrawSystem extends AbstractDrawableSystem {

    private BitmapFont font;

    public TextDrawSystem(AbstractRoom room) {
        super(room);

        font = new BitmapFont(Gdx.files.classpath("font/bit.fnt"));
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(TextComponent.class, PositionComponent.class);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        room.spriteBatch.setProjectionMatrix(camera.combined);
        room.spriteBatch.begin();
        gobs.forEach(gob -> gob.forEach(TextComponent.class, text -> gob.forEach(PositionComponent.class, positionComponent -> {
            font.getData().setScale(text.scale);
            font.setColor(text.color);
            font.draw(spriteBatch, text.text, positionComponent.x + text.getOffset().x, positionComponent.y + text.getOffset().y);
        })));
        room.spriteBatch.end();
    }
}
