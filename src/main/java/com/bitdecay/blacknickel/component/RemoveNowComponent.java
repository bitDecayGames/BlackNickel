package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component marks an object as one to be removed in the next cleanup.
 */
public class RemoveNowComponent extends AbstractComponent {

    public RemoveNowComponent(MyGameObject obj){super(obj);}
}
