package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * The component in charge of tracking the x and y acceleration of a NON-PHYSICS object
 */
public class AccelerationComponent extends AbstractComponent {
    public float x = 0;
    public float y = 0;

    public AccelerationComponent(MyGameObject obj){super(obj);}
    public AccelerationComponent(MyGameObject obj, float x, float y){
        super(obj);
        this.x = x;
        this.y = y;
    }

    public AccelerationComponent setAcceleration(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Immutable
     * @return new Vector2
     */
    public Vector2 toVector2(){
        return new Vector2(x, y);
    }
}
