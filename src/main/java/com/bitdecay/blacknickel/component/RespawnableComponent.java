package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.trait.IInitializable;

/**
 * The component in charge of tracking the x and y position of the respawn point of the object.
 */
public class RespawnableComponent extends AbstractComponent implements IInitializable {
    private float x = 0;
    private float y = 0;
    private boolean initialized = false;

    public RespawnableComponent(MyGameObject obj){super(obj);}
    private RespawnableComponent(MyGameObject obj, float x, float y){
        super(obj);
        this.x = x;
        this.y = y;
    }

    /**
     * Immutable
     * @return new Vector2
     */
    public Vector2 toVector2(){
        return new Vector2(x, y);
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void initialize(AbstractRoom room) {
        obj.forEach(PositionComponent.class, pos -> {
            x = pos.x;
            y = pos.y;
        });
        initialized = true;
    }
}
