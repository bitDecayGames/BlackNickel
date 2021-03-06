package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.trait.IExecutable;

import java.util.function.BiConsumer;

/**
 * The timer component will execute a given function (with a ref to this component's game object) when a given number of seconds has gone by.  You can reset the timer within the function by setting 'done' = false and setting the 'seconds' value to something > 0.  You can also remove the component by calling obj.removeComponent.
 */
public class TimerComponent extends AbstractComponent implements IExecutable {
    public boolean done = false;
    public float seconds;
    private BiConsumer<MyGameObject, TimerComponent> func;

    public TimerComponent(MyGameObject obj, float seconds, BiConsumer<MyGameObject, TimerComponent> func) {
        super(obj);
        this.seconds = seconds;
        this.func = func;
    }

    @Override
    public void execute(){
        func.accept(obj, this);
    }
}
