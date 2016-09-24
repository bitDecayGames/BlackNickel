package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * The component in charge of tracking the x and y position of the object.
 */
public class PositionComponent extends AbstractComponent {
    public float x = 0;
    public float y = 0;

    public PositionComponent(MyGameObject obj){super(obj);}
    public PositionComponent(MyGameObject obj, float x, float y){
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
}
