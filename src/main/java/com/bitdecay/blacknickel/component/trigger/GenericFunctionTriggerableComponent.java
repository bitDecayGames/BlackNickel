package com.bitdecay.blacknickel.component.trigger;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

import java.util.function.BiConsumer;

/**
 * This triggerable will just take a function
 */
public class GenericFunctionTriggerableComponent extends TriggerableComponent {

    private BiConsumer<MyGameObject, TriggerComponent> func;

    public GenericFunctionTriggerableComponent(MyGameObject obj, BiConsumer<MyGameObject, TriggerComponent> func) {
        super(obj);
        this.func = func;
    }

    @Override
    public void execute(TriggerComponent origin) { func.accept(obj, origin); }
}
