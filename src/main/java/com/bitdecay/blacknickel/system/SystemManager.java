package com.bitdecay.blacknickel.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.system.abstracted.AbstractSystem;
import com.bitdecay.blacknickel.trait.ICleanup;
import com.bitdecay.blacknickel.trait.IDrawWithCamera;
import com.bitdecay.blacknickel.trait.IRefreshable;
import com.bitdecay.blacknickel.trait.IUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The system manager is more of a helper class than anything.  It just facilitates all of the systems being "processed" and "refreshed".  As long as you add the system to the room, it will get added to the SystemManager.
 */
public class SystemManager implements ICleanup, IRefreshable, IUpdate, IDrawWithCamera {

    private boolean dirty = false;
    private final List<AbstractSystem> systems = new ArrayList<>();
    private final List<AbstractSystem> systemsToRemove = new ArrayList<>();
    private final List<AbstractSystem> systemsToAdd = new ArrayList<>();

    public <T extends AbstractSystem> Optional<T> getSystem(Class<T> systemClass){
        return systems.stream().filter(systemClass::isInstance).findFirst().map(systemClass::cast);
    }

    public SystemManager addSystem(AbstractSystem system){
        systemsToAdd.add(system);
        dirty = true;
        return this;
    }

    public <T extends AbstractSystem> Optional<T> removeSystem(Class<T> systemClass){
        Optional<T> system = getSystem(systemClass);
        if (system.isPresent()) {
            systemsToRemove.add(system.get());
            dirty = true;
        }
        return system;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void cleanup() {
        systemsToRemove.forEach(systems::remove);
        systemsToRemove.clear();
        systemsToAdd.forEach(systems::add);
        systemsToAdd.clear();
        dirty = false;
    }

    @Override
    public void refresh(List<MyGameObject> gobs) {
        systems.forEach(sys -> sys.refresh(gobs));
    }

    @Override
    public void update(float delta) {
        systems.forEach(sys -> {
            if (sys instanceof IUpdate) ((IUpdate) sys).update(delta);
        });

    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        systems.forEach(sys -> {
            if (sys instanceof IDrawWithCamera) ((IDrawWithCamera) sys).draw(spriteBatch, camera);
        });
    }
}
