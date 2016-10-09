package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This triggerable will change the image to a given path
 */
public class ChangeImageTriggerableComponent extends TriggerableToggleComponent {

    private String originalPath = null;
    private String path;

    public ChangeImageTriggerableComponent(MyGameObject obj, Config conf) {
        super(obj, conf);
        this.path = conf.getString("path");
    }

    @Override
    public void turnOn(TriggererComponent source, TriggerComponent origin) {
        turn(path);
    }

    @Override
    public void turnOff(TriggererComponent source, TriggerComponent origin) {
        turn(originalPath);
    }

    private void turn(String newPath){
        obj.getComponent(DrawableComponent.class).ifPresent(drawableComponent -> {
            if (drawableComponent instanceof StaticImageComponent){
                if (originalPath == null) originalPath = ((StaticImageComponent) drawableComponent).path();
                new StaticImageComponent(obj, newPath).addSelfToGameObject();
                obj.removeComponent(drawableComponent);
            }
        });
    }
}
