package com.bitdecay.blacknickel.system;

import com.bitdecay.blacknickel.component.OverlapTriggerComponent;
import com.bitdecay.blacknickel.component.OverlapTriggererComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.gameobject.MyGameObjectUtils;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.system.abstracted.AbstractUpdatableSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This system is responsible for executing triggers when they are overlapped by a matching object
 */
public class OverlapTriggerSystem extends AbstractUpdatableSystem {

    private List<GobTup> contacts = new ArrayList<>();

    public OverlapTriggerSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(OverlapTriggerComponent.class) || gob.hasComponent(OverlapTriggererComponent.class);
    }

    @Override
    public void update(float delta) {
        resetContacts();
        gobs.forEach(a -> gobs.forEach(b -> {
            if (a != b && a.hasComponent(OverlapTriggerComponent.class) && b.hasComponent(OverlapTriggererComponent.class)) {
                boolean overlap = MyGameObjectUtils.overlap(a, b);
                if (overlap) {
                    Optional<GobTup> contact = inContacts(a, b);
                    if (contact.isPresent()) contact.get().stillInContact = true;
                    else {
                        addToContacts(a, b);
                        a.forEach(OverlapTriggerComponent.class, OverlapTriggerComponent::execute);
                    }
                }
            }
        }));
        cleanupContacts();
    }

    private void resetContacts(){
        contacts.forEach(tup -> tup.stillInContact = false);
    }

    private Optional<GobTup> inContacts(MyGameObject a, MyGameObject b){
        for (GobTup tup : contacts) if (tup.exists(a, b)) return Optional.of(tup);
        return Optional.empty();
    }

    private void addToContacts(MyGameObject a, MyGameObject b){
        contacts.add(new GobTup(a, b));
    }

    private void cleanupContacts(){
        if (contacts.size() > 0) contacts.removeAll(contacts.stream().filter(tup -> !tup.stillInContact).collect(Collectors.toList()));
    }

    private class GobTup{
        public MyGameObject a;
        public MyGameObject b;
        public boolean stillInContact = true;

        public GobTup(MyGameObject a, MyGameObject b){
            this.a = a;
            this.b = b;
        }

        public boolean exists(MyGameObject a, MyGameObject b){
            return (this.a == a && this.b == b) || (this.a == b && this.b == a);
        }
    }
}
