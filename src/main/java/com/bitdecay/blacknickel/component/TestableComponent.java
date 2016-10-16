package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This calls out that an object is a testable object (it was contained within a green circle at the start of a level)
 */
public class TestableComponent extends AbstractComponent {
    public TestableComponent(MyGameObject obj) { super(obj); }
}
