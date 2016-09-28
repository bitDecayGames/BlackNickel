package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.ScreenRumbleComponent;
import com.bitdecay.blacknickel.component.ScreenShakeOnCreateComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractUpdatableSystem;

/**
 * This system will make the screen shake if objects have certain components
 */
public class ScreenShakeSystem extends AbstractUpdatableSystem {
    public ScreenShakeSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        // logic goes here because this method is only called when gobs changes
        gob.forEach(ScreenShakeOnCreateComponent.class, shake -> {
            room.camera.shake(shake.duration());
            gob.removeComponent(ScreenShakeOnCreateComponent.class); // only happens once per component
        });
        return gob.hasComponent(ScreenRumbleComponent.class);
    }

    @Override
    public void update(float delta) {
        if (gobs.size() > 0) room.camera.rumble();
    }
}
