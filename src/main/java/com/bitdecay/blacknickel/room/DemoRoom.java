package com.bitdecay.blacknickel.room;


import com.bitdecay.blacknickel.component.UnderControlComponent;
import com.bitdecay.blacknickel.gameobject.MyGameObjectFactory;
import com.bitdecay.blacknickel.screen.GameScreen;
import com.bitdecay.blacknickel.system.*;
import com.bitdecay.jump.level.Level;

/**
 * The demo room is just a super simple example of how to add systems and game objects to a room.
 */
public class DemoRoom extends AbstractRoom {

    public DemoRoom(GameScreen gameScreen, Level level) {
        super(gameScreen, level);

        // systems must be added before game objects
        new InitializationSystem(this);
        new PhysicsSystem(this);
        new TimerSystem(this);
        new CameraUpdateSystem(this);
        new FreestyleMovementSystem(this);
        new ShellMovementSystem(this);
        new PossessionSystem(this);
        new ExitPossessionSystem(this);
        new RespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new DespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new ShapeDrawSystem(this);
        new DrawSystem(this);
        new RemovalSystem(this);

        gobs.add(MyGameObjectFactory.objectFromConf("Ghost", 0, 50).addComponent(UnderControlComponent.class));
        gobs.add(MyGameObjectFactory.objectFromConf("Shell", 0, 30));
    }
}
