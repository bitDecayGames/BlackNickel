package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.graphics.Color;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This component is for tinting drawable components a specific color
 */
public class TintComponent extends AbstractComponent {

    private Color color;

    public TintComponent(MyGameObject obj, Color color) {
        super(obj);
        this.color = color;
    }

    public TintComponent(MyGameObject obj, Config conf){
        super(obj, conf);
        if (conf.hasPath("hex")) color = Color.valueOf(conf.getString("hex"));
        else {
            Color c = Color.WHITE.cpy();
            c.r = (float) conf.getDouble("r");
            c.g = (float) conf.getDouble("g");
            c.b = (float) conf.getDouble("b");
            c.a = (float) conf.getDouble("a");
            color = c;
        }
    }

    public Color color(){ return color; }
}
