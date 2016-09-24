package com.bitdecay.blacknickel.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.jump.gdx.level.EditorIdentifierObject;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Static access to the tilesets defined within the application.conf file
 */
public final class Tilesets {
    private Tilesets(){}

    private static List<Tileset> tilesets;

    private static void initTilesets(){
        if (tilesets == null){
            tilesets = new ArrayList<>();
            Launcher.conf.getList("tilesets").forEach(confValue -> {
                if (confValue instanceof ConfigObject){
                    Config conf = ((ConfigObject) confValue).toConfig();
                    String name = conf.getString("name");
                    String path = conf.getString("path");
                    int material = conf.getInt("material");
                    if (name != null && path != null) {
                        List<TextureRegion> regions = Arrays.asList(MyGame.ATLAS.findRegions(path).toArray());
                        Tileset t = new Tileset(name, material, regions);
                        tilesets.add(t);
                    }
                }
            });
        }
    }

    public static List<EditorIdentifierObject> editorTilesets(){
        initTilesets();
        return tilesets.stream().map(ts -> new EditorIdentifierObject(ts.material(), ts.name(), ts.icon())).collect(Collectors.toList());
    }

    public static List<Tileset> tilesets(){
        initTilesets();
        List<Tileset> t = new ArrayList<>();
        t.addAll(tilesets);
        return t;
    }

    public static Tileset tilesetForMaterial(int material){
        initTilesets();
        return tilesets.stream().filter(t -> t.material == material).findFirst().get();
    }

    public static class Tileset {
        private String name;
        private int material;
        private List<TextureRegion> regions;

        public Tileset(String name, int material, List<TextureRegion> regions){
            this.name = name;
            this.material = material;
            this.regions = regions;
        }

        public String name() {
            return name;
        }

        public int material() {
            return material;
        }

        public List<TextureRegion> regions() {
            return regions;
        }

        public TextureRegion icon() {
            return regions.get(0);
        }
    }
}
