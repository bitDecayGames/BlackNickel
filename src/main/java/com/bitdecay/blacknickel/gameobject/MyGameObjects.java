package com.bitdecay.blacknickel.gameobject;

import com.bitdecay.blacknickel.trait.ICleanup;
import com.bitdecay.blacknickel.trait.IRefreshable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * This is a collection class.  It contains a list of game objects.  In its constructor, you also need to provide a list of refreshables.  Those refreshables have their refresh method called when any of the game objects are dirty.
 */
public class MyGameObjects implements ICleanup {
    private boolean dirty = false;

    private final List<MyGameObject> gobs = new ArrayList<>();
    private final List<MyGameObject> gobsToAdd = new ArrayList<>();
    private final List<MyGameObject> gobsToRemove = new ArrayList<>();
    private final List<IRefreshable> refreshables = new ArrayList<>();


    public MyGameObjects(IRefreshable... refreshables){
        this.refreshables.addAll(Arrays.asList(refreshables));
    }

    public MyGameObjects add(MyGameObject gob){
        gobsToAdd.add(gob);
        dirty = true;
        return this;
    }

    public Optional<MyGameObject> remove(MyGameObject gob){
        if (gobs.contains(gob)){
            gobsToRemove.add(gob);
            dirty = true;
            return Optional.of(gob);
        }
        return Optional.empty();
    }

    public MyGameObjects forEach(Consumer<MyGameObject> forEach){
        gobs.forEach(forEach);
        return this;
    }

    public MyGameObjects clear(){
        gobsToRemove.addAll(gobs);
        return this;
    }

    public int size(){
        return gobs.size();
    }

    @Override
    public boolean isDirty() {
        return gobs.stream().filter(MyGameObject::isDirty).findFirst().isPresent() || dirty;
    }

    @Override
    public void cleanup() {
        dirty = false; // this goes at the beginning so that the next steps could actually make it dirty again
        gobsToRemove.forEach(gobs::remove);
        gobsToRemove.clear();
        gobsToAdd.forEach(gobs::add);
        gobsToAdd.clear();
        gobs.forEach(MyGameObject::cleanup);
        refreshables.forEach(r -> r.refresh(gobs));
    }
}
