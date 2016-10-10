package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This is the component that GETS triggered by the TriggerComponent and will toggle back and forth
 */
public abstract class TriggerableSelfRemovingComponent extends TriggerableComponent {

    private boolean removed = false;

    public TriggerableSelfRemovingComponent(MyGameObject obj) {
        super(obj);
    }

    public TriggerableSelfRemovingComponent(MyGameObject obj, Config conf) { super(obj, conf); }

    @Override
    public final void execute(TriggererComponent source, TriggerComponent origin){
        if (!removed) {
            innerExecute(source, origin);
            obj.removeComponent(this);
            removed = true;
        }
    }

    protected abstract void innerExecute(TriggererComponent source, TriggerComponent origin);
}
