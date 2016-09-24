package com.bitdecay.blacknickel.system.abstracted;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.trait.IRefreshable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The System objects are where most of the game logic lives in an Entity Component System implementation.  If you find that you are writing an update method inside one of the components, then you are probably doing it wrong.  Components should just be keeping track of state.  While systems actually do something with that state.  For this abstract system, the goal was to take care of all of the boilerplate code.  Each system has it's own list of gobs.  The reason for this is because of all the game objects, each system only cares about the few that meet the criteria.  The validateGob method analyzes each game object and decides whether or not it should be a part of the internal list of game objects.  The refresh method is where the validateGob method is used.  Refresh gets called when ever there is a change to any of the game objects.  That includes adding or removing a game object, or adding or removing a component from a game object.
 */
public abstract class AbstractSystem implements IRefreshable {

    protected final AbstractRoom room;
    protected final List<MyGameObject> gobs = new ArrayList<>();

    public AbstractSystem(AbstractRoom room){
        this.room = room;
        this.room.systemManager.addSystem(this);
    }

    public final void refresh(List<MyGameObject> gobs){
        this.gobs.clear();
        this.gobs.addAll(gobs.stream().filter(this::validateGob).collect(Collectors.toList()));
    }

    protected abstract boolean validateGob(MyGameObject gob);

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
