package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.trait.IOffsetable;
import com.typesafe.config.Config;

/**
 * This component, when added to a game object, will draw a circle at the current position of the game object.
 */
public class TextComponent extends AbstractComponent implements IOffsetable {
    public final String text;
    public final Color color;
    public final float scale;
    private final Vector2 offset;

    public TextComponent(MyGameObject obj, String text, Color color, float scale, Vector2 offset){
        super(obj);
        this.text = text;
        this.color = color.cpy();
        this.scale = scale;
        this.offset = offset.cpy();
    }

    public TextComponent(MyGameObject obj, String text, Color color, float scale){
        this(obj, text, color, scale, new Vector2(0, 0));
    }


    public TextComponent(MyGameObject obj, Config conf) {
        super(obj, conf);
        text = conf.getString("text");
        Config colorConf = conf.getConfig("color");
        color = new Color(
                (float) colorConf.getDouble("r"),
                (float) colorConf.getDouble("g"),
                (float) colorConf.getDouble("b"),
                (float) colorConf.getDouble("a")
        );
        scale = (float) conf.getDouble("scale");
        if (conf.hasPath("offset")) offset = new Vector2(
                    (float) conf.getDouble("offset.x"),
                    (float) conf.getDouble("offset.y")
            );
        else offset = new Vector2(0, 0);
    }

    @Override
    public Vector2 getOffset() {
        return offset;
    }
}
