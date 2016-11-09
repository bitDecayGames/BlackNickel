package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component is for controlling particles
 */
public class ParticleComponent extends DrawableComponent {

    protected TextureRegion particleImage;
    public float particleLifespan;

    public ParticleComponent(MyGameObject obj, TextureRegion particleImage, float particleLifespan) {
        super(obj);
        this.particleImage = particleImage;
        this.particleLifespan = particleLifespan;
    }

    public ParticleComponent(MyGameObject obj, String particleImage, float particleSize, float particleLifespan) { this(obj, MyGame.ATLAS.findRegion(particleImage), particleLifespan); }

    @Override
    public TextureRegion image() {
        return particleImage;
    }
}
