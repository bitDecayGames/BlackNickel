package com.bitdecay.blacknickel.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.gameobject.MyGameObject;
import com.typesafe.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This component is for emitting particles
 */
public class ParticleEmitterComponent extends AbstractComponent {

    protected int particlesPerSecond;
    protected int maxParticleCount;
    protected TextureRegion particleImage;
    protected float particleSize;
    protected float particleLifespan;

    private float partialParticleCounter = 0;
    private List<ParticleComponent> particles = new ArrayList<>();

    public ParticleEmitterComponent(MyGameObject obj, int particlesPerSecond, int maxParticleCount, String particleImage, float particleSize, float particleLifespan) {
        super(obj);
        this.particlesPerSecond = particlesPerSecond;
        this.maxParticleCount = maxParticleCount;
        this.particleImage = MyGame.ATLAS.findRegion(particleImage);
        this.particleSize = particleSize;
        this.particleLifespan = particleLifespan;
    }

    public ParticleEmitterComponent(MyGameObject obj, Config conf){
        super(obj, conf);
        this.particlesPerSecond = conf.getInt("rate");
        this.maxParticleCount = conf.getInt("max");
        this.particleImage = MyGame.ATLAS.findRegion(conf.getString("particle.img"));
        this.particleSize = (float) conf.getDouble("particle.size");
        this.particleLifespan = (float) conf.getDouble("particle.lifespan");
    }

    final public List<MyGameObject> step(float delta){
        float particlesToCreateThisStep = delta * particlesPerSecond;
        float partialParticles = particlesToCreateThisStep - (int) particlesToCreateThisStep;
        partialParticleCounter += partialParticles;
        if (partialParticleCounter > 1) {
            particlesToCreateThisStep = partialParticleCounter;
            partialParticleCounter = particlesToCreateThisStep - (int) particlesToCreateThisStep;
        }
        int particlesToCreate = (int) particlesToCreateThisStep;
        List<MyGameObject> objs = new ArrayList<>();
        for (; particlesToCreate > 0 && particles.size() < maxParticleCount; particlesToCreate--) objs.add(createParticle());
        particles = particles.stream().filter(pc ->
                pc.particleLifespan > 0).collect(Collectors.toList());
        return objs;
    }

    protected MyGameObject createParticle(){
        MyGameObject o = new MyGameObject();
        new SizeComponent(o, particleSize, particleSize).addSelfToGameObject();
        ParticleComponent particle = new ParticleComponent(o, particleImage, particleLifespan);
        particle.addSelfToGameObject();
        particles.add(particle);
        new VelocityComponent(o, MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)).addSelfToGameObject();
        return o;
    }
}
