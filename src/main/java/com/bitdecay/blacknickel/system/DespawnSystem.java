package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.DespawnableComponent;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.component.RemoveNowComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of updating the position of an object when the object goes out of the window and must respawn
 */
public class DespawnSystem extends AbstractForEachUpdatableSystem {
    private int minWidth;
    private int maxWidth;
    private int minHeight;
    private int maxHeight;


    public DespawnSystem(AbstractRoom room, int minWidth, int maxWidth, int minHeight, int maxHeight) {
        super(room);
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(DespawnableComponent.class, PositionComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(PositionComponent.class, pos -> {
            if (pos.x < minWidth || pos.x > maxWidth || pos.y < minHeight || pos.y > maxHeight) gob.addComponent(new RemoveNowComponent(gob));
        });
    }

}
