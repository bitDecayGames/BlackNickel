package com.bitdecay.blacknickel.component;

import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.bitdecay.blacknickel.room.AbstractRoom;
import com.bitdecay.blacknickel.trait.IInitializable;
import com.bitdecay.blacknickel.trait.IRemovable;
import com.bitdecay.jump.BitBody;
import com.bitdecay.jump.BodyType;
import com.bitdecay.jump.JumperBody;
import com.bitdecay.jump.geom.BitRectangle;
import com.bitdecay.jump.properties.JumperProperties;
import com.bitdecay.jump.render.JumperRenderStateWatcher;
import com.typesafe.config.Config;

/**
 * The component in charge of tracking the BitBody of the object
 */
public class PhysicsComponent extends AbstractComponent implements IInitializable, IRemovable {
    private BitBody body;
    private boolean initialized = false;

    private PhysicsComponent(MyGameObject obj, BitBody body){
        super(obj);
        this.body = body;
    }

    private PhysicsComponent(MyGameObject obj, float width, float height, int jumpStrength, int jumpCount, float jumpVariableHeightWindow, int deceleration, int acceleration, int airAcceleration, boolean jumpHittingHeadStopsJump){
        super(obj);
        JumperBody body = new JumperBody();
        body.props.deceleration = deceleration;
        body.props.acceleration = acceleration;
        body.props.airAcceleration = airAcceleration;
        body.jumperProps = new JumperProperties();
        body.jumperProps.jumpStrength = jumpStrength;
        body.jumperProps.jumpCount = jumpCount;
        body.jumperProps.jumpVariableHeightWindow = jumpVariableHeightWindow;
        body.bodyType = BodyType.DYNAMIC;
        body.aabb.set(new BitRectangle(0, 0, width, height));
        body.userObject = this;
        body.renderStateWatcher = new JumperRenderStateWatcher();
        body.jumperProps.jumpHittingHeadStopsJump = jumpHittingHeadStopsJump;
        this.body = body;
    }

    public PhysicsComponent(MyGameObject obj, Config conf) {
        this(obj,
                (float) conf.getDouble("width"),
                (float) conf.getDouble("height"),
                conf.getInt("jumpStrength"),
                conf.getInt("jumpCount"),
                (float) conf.getDouble("jumpVariableHeightWindow"),
                conf.getInt("deceleration"),
                conf.getInt("acceleration"),
                conf.getInt("airAcceleration"),
                conf.getBoolean("jumpHittingHeadStopsJump"));
    }

    public BitBody body() {
        return body;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void initialize(AbstractRoom room) {
        obj.forEach(PositionComponent.class, pos -> {
            body.aabb.xy.set(pos.x, pos.y);
        });
        room.getWorld().addBody(body);
        initialized = true;
    }

    @Override
    public void remove(AbstractRoom room) {
        room.getWorld().removeBody(body);
    }
}
