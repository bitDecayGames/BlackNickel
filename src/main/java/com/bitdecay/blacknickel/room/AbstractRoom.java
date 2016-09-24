package com.bitdecay.blacknickel.room;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.camera.FollowOrthoCamera;
import com.bitdecay.blacknickel.gameobject.MyGameObjectFactory;
import com.bitdecay.blacknickel.gameobject.MyGameObjects;
import com.bitdecay.blacknickel.screen.GameScreen;
import com.bitdecay.blacknickel.system.SystemManager;
import com.bitdecay.blacknickel.trait.*;
import com.bitdecay.blacknickel.util.RunMode;
import com.bitdecay.jump.collision.BitWorld;
import com.bitdecay.jump.gdx.level.EditorIdentifierObject;
import com.bitdecay.jump.gdx.level.RenderableLevelObject;
import com.bitdecay.jump.level.Level;
import com.bitdecay.jump.level.TileObject;
import com.bitdecay.jump.leveleditor.EditorHook;
import com.bitdecay.jump.leveleditor.render.LibGDXWorldRenderer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;

/**
 * The room object is an added layer to the GameScreen class.  Instead of having all of the game logic reside in the GameScreen, it now will reside in the AbstractRoom and the individual room implementations.  Think of it like: you have one room for each level in the game and one room for each boss fight in the game.  The abstract room takes care of all the updating and drawing of everything.  However, if you have special requirements, you can always override the update or draw methods.  Just make sure to call super.update() somewhere in your override.  Several configuration values are set at the top of this class using the conf files in resources/conf.  Each room is separate of every other room so you need to go through the initialization each time you create a new room.  The reason for this is to limit cross talk.  We don't want bugs that span multiple rooms...  That would make it very difficult to debug.
 */
public abstract class AbstractRoom implements IUpdate, IDraw, IHasScreenSize, ICanSetScreen, EditorHook, IDisposable {

    protected final GameScreen gameScreen;
    public final SystemManager systemManager = new SystemManager();
    protected final MyGameObjects gobs = new MyGameObjects(systemManager);
    protected final List<ICleanup> cleanupables = Arrays.asList(systemManager, gobs);

    public final SpriteBatch spriteBatch = new SpriteBatch();
    public final ShapeRenderer shapeRenderer = new ShapeRenderer();
    public final FollowOrthoCamera camera = new FollowOrthoCamera(Launcher.conf.getInt("resolution.camera.width"), Launcher.conf.getInt("resolution.camera.height"));

    protected final LibGDXWorldRenderer worldRenderer = new LibGDXWorldRenderer();
    protected final BitWorld world = new BitWorld();
    protected Level level;

    public AbstractRoom(GameScreen gameScreen, Level level){
        this.gameScreen = gameScreen;

        camera.maxZoom = (float) Launcher.conf.getDouble("resolution.camera.maxZoom");
        camera.minZoom = (float) Launcher.conf.getDouble("resolution.camera.minZoom");
        camera.snapSpeed = (float) Launcher.conf.getDouble("resolution.camera.snapSpeed");
        camera.buffer = 100;

        world.setGravity((float) Launcher.conf.getDouble("world.gravity.x"), (float) Launcher.conf.getDouble("world.gravity.y"));

        levelChanged(level);
    }

    public MyGameObjects getGameObjects(){
        return gobs;
    }

    public void render(float delta){
        update(delta);
        draw(spriteBatch);
    }

    @Override
    public void update(float delta) {
        cleanupables.forEach(c -> {
            if (c.isDirty()) c.cleanup();
        });

        camera.update(delta);
        world.step(delta);

        systemManager.update(delta);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        render(camera);
    }

    @Override
    public Vector2 screenSize() {
        return this.gameScreen.screenSize();
    }

    @Override
    public void setScreen(Screen screen) {
        gameScreen.setScreen(screen);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    // /////////////////////////////////////
    // Level editor hook methods
    // /////////////////////////////////////

    @Override
    public void render(OrthographicCamera cam) {
        if (MyGame.RUN_MODE == RunMode.DEV) worldRenderer.render(world, cam);
        systemManager.draw(spriteBatch, cam);

    }

    @Override
    public final BitWorld getWorld() {
        return world;
    }

    @Override
    public final List<EditorIdentifierObject> getTilesets() {
        throw new NotImplementedException();
    }

    @Override
    public final List<EditorIdentifierObject> getThemes() {
        throw new NotImplementedException();
    }

    @Override
    public final List<RenderableLevelObject> getCustomObjects() {
        throw new NotImplementedException();
    }

    @Override
    public final void levelChanged(Level level) {
        world.removeAllBodies();
        world.setLevel(level);
        this.level = level;

        gobs.clear();
        gobs.cleanup();
        // generate game objects from level tiles
        level.layers.layers.forEach((index, layer)->{
            for (int x = 0; x < layer.grid.length; x++) {
                for (int y = 0; y < layer.grid[0].length; y++) {
                    TileObject obj = layer.grid[x][y];
                    if (obj != null) gobs.add(MyGameObjectFactory.tile(obj));
                }
            }
        });
        // generate game objects from renderable level objects
        level.layers.layers.forEach((index, layer) -> layer.otherObjects.forEach((uuid, levelObject) -> gobs.add(MyGameObjectFactory.objectFromConf(levelObject.name(), levelObject.rect.xy.x, levelObject.rect.xy.y))));
        gobs.cleanup();
    }
}
