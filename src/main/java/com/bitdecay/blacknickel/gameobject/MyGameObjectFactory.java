package com.bitdecay.blacknickel.gameobject;

import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.component.SizeComponent;
import com.bitdecay.blacknickel.component.TileComponent;
import com.bitdecay.blacknickel.editor.ConfBasedLevelObject;
import com.bitdecay.blacknickel.util.Tilesets;
import com.bitdecay.jump.gdx.level.RenderableLevelObject;
import com.bitdecay.jump.level.TileObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The idea here is to provide a single place for you to add your game objects.  You know that the "Player" game object will have a PositionComponent, a SizeComponent, and a CameraFollowComponent.  So in a static method (maybe called buildPlayer) you want to create a generic MyGameObject and populate it with the correct components.
 */
public final class MyGameObjectFactory {
    private MyGameObjectFactory(){}

    public static MyGameObject objectFromConf(String name, float x, float y){
        // hmmm this is basically just a wrapper... i'm not sure if this is the right way to do it
        return MyGameObjectFromConf.objectFromConf(name, x, y);
    }

    public static List<RenderableLevelObject> allLevelObjects(){
        return MyGameObjectFromConf.objectConfs().stream().map(config -> new ConfBasedLevelObject(config.getString("name"))).map(RenderableLevelObject.class::cast).collect(Collectors.toList());
    }

    public static MyGameObject tile(TileObject tile){
        Tilesets.Tileset tileset = Tilesets.tilesetForMaterial(tile.material);
        MyGameObject t = new MyGameObject();
        t.addComponent(new PositionComponent(t, tile.rect.xy.x, tile.rect.xy.y));
        t.addComponent(new SizeComponent(t, tile.rect.width, tile.rect.height));
        t.addComponent(new TileComponent(t, tileset.regions().get(tile.renderNValue)));
        return t;
    }
}
