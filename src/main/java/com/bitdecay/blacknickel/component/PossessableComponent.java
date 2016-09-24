package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component allows for possession
 */
public class PossessableComponent extends AbstractComponent {

    public MyGameObject possessor = null;

    public PossessableComponent(MyGameObject obj) {
        super(obj);
    }
}
