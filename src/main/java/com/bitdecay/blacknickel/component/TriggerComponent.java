package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.trait.IExecutable;

import java.util.function.Consumer;

/**
 * This component allows for function triggers
 */
public class TriggerComponent extends AbstractComponent implements IExecutable {

    private Consumer<MyGameObject> func;

    public TriggerComponent(MyGameObject obj, Consumer<MyGameObject> func) {
        super(obj);
        this.func = func;
    }

    @Override
    public void execute() {
        func.accept(obj);
    }
}
