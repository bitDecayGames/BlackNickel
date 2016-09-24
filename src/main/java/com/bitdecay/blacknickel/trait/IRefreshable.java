package com.bitdecay.blacknickel.trait;

import com.bitdecay.blacknickel.gameobject.MyGameObject;

import java.util.List;

/**
 * Object is refreshable with a list of game objects.  Usually only called when the list of game objects has changed in some way.  Such as there being a new game object or a removed game object.
 */
public interface IRefreshable {
    void refresh(List<MyGameObject> gobs);
}
