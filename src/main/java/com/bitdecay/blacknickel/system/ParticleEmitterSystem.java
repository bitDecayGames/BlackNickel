package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.ParticleEmitterComponent;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of creating particles based on a particle emitter
 */
public class ParticleEmitterSystem extends AbstractForEachUpdatableSystem {

    public ParticleEmitterSystem(AbstractRoom room) { super(room); }

    @Override
    protected boolean validateGob(MyGameObject gob) { return gob.hasComponents(ParticleEmitterComponent.class, PositionComponent.class); }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(ParticleEmitterComponent.class, emitter -> gob.forEach(PositionComponent.class, position -> emitter.step(delta).forEach(newParticle -> {
            new PositionComponent(newParticle, position.x, position.y).addSelfToGameObject();
            room.getGameObjects().add(newParticle);
        })));
    }

}
