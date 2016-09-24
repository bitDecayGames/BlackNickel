package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component is for drawing an icon in the editor
 */
public class IconComponent extends AbstractComponent {

    private TextureRegion icon;

    public IconComponent(MyGameObject obj, String imagePath) {
        super(obj);
        icon = MyGame.ATLAS.findRegion(imagePath);
    }

    public TextureRegion icon(){ return icon; }
}
