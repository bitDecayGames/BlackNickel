package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.component.PhysicsComponent;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.component.RespawnableComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of updating the position of an object when the object goes out of the window and must respawn
 */
public class RespawnSystem extends AbstractForEachUpdatableSystem {
    private int minWidth;
    private int maxWidth;
    private int minHeight;
    private int maxHeight;


    public RespawnSystem(AbstractRoom room, int minWidth, int maxWidth, int minHeight, int maxHeight) {
        super(room);
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(RespawnableComponent.class, PositionComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(RespawnableComponent.class, res ->
            gob.forEach(PositionComponent.class, pos -> {
                if (pos.x < minWidth || pos.x > maxWidth || pos.y < minHeight || pos.y > maxHeight) {
                    Vector2 resPos = res.toVector2();
                    pos.x = resPos.x;
                    pos.y = resPos.y;

                    gob.forEach(PhysicsComponent.class, phy -> {
                        phy.body().aabb.xy.x = resPos.x;
                        phy.body().aabb.xy.y = resPos.y;
                        phy.body().velocity = phy.body().velocity.scale(0);
                    });
                }
            })
        );
    }

}
