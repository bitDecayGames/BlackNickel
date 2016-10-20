package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.ActivationTriggerComponent;
import com.bitdecay.blacknickel.component.ActivationTriggererComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.gameobject.MyGameObjectUtils;
import com.bitdecay.blacknickel.input.Key;
import com.bitdecay.blacknickel.input.Keyboard;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractUpdatableSystem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This system is responsible for executing triggers when they are overlapped by a matching object
 */
public class ActivationTriggerSystem extends AbstractUpdatableSystem {

    private List<Key> interactButtons = Launcher.conf.getConfig("controls").getConfig("keyboard").getStringList("action").stream().map(Key::fromString).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

    public ActivationTriggerSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(ActivationTriggerComponent.class) || gob.hasComponent(ActivationTriggererComponent.class);
    }

    @Override
    public void update(float delta) {
        gobs.forEach(a -> gobs.forEach(b -> {
            if (a != b && Keyboard.isAtLeastOneKeyJustPressed(interactButtons) && a.hasComponent(ActivationTriggerComponent.class) && b.hasComponent(ActivationTriggererComponent.class) && MyGameObjectUtils.overlap(a, b)) {
                ActivationTriggererComponent bTriggerer = b.getComponent(ActivationTriggererComponent.class).get();
                a.forEach(ActivationTriggerComponent.class, atc -> atc.execute(bTriggerer));
            }
        }));
    }
}
