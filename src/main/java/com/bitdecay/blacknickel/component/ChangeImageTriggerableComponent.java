package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This triggerable will change the image to a given path
 */
public class ChangeImageTriggerableComponent extends TriggerableComponent {

    private String path;

    public ChangeImageTriggerableComponent(MyGameObject obj, Config conf) {
        super(obj, conf);
        this.path = conf.getString("path");
    }

    @Override
    public void execute(TriggerComponent origin) {
        log.debug("Triggered!!!");
        obj.getComponent(DrawableComponent.class).ifPresent(drawableComponent -> {
            if (drawableComponent instanceof StaticImageComponent){
                new StaticImageComponent(obj, path).addSelfToGameObject();
                obj.removeComponent(drawableComponent);
            }
        });
    }
}
