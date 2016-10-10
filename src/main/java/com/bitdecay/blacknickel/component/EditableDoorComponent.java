package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

/**
 * This component is mostly for the level editor for when you want to add a level to a door object
 */
public class EditableDoorComponent extends AbstractComponent {

    // this is just to facilitate the transfer of this data to the triggerable component
    public String level = "";

    public EditableDoorComponent(MyGameObject obj){
        super(obj);
    }
}
