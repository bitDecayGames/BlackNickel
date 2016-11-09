package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.ParticleComponent;
import com.bitdecay.blacknickel.component.RemoveNowComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of destroying particles based on their lifespan
 */
public class ParticleSystem extends AbstractForEachUpdatableSystem {

    public ParticleSystem(AbstractRoom room) { super(room); }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(ParticleComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(ParticleComponent.class, particle -> {
            if (particle.particleLifespan <= 0) gob.addComponent(RemoveNowComponent.class);
            else particle.particleLifespan -= delta;
        });
    }

}
