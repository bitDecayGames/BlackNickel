package com.bitdecay.blacknickel.editor;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.util.TexturePackerUtils;
import com.bitdecay.jump.leveleditor.render.LevelEditor;

/**
 * Launches the editor around the game
 */
public class EditorLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Launcher.conf.getInt("resolution.default.width");
        config.height = Launcher.conf.getInt("resolution.default.height");
        config.title = Launcher.conf.getString("title");
        config.fullscreen = false;

        TexturePackerUtils.pack();

        LevelEditor.setAssetsFolder("../jump/jump-leveleditor/assets");
        new LwjglApplication(new EditorApp(), config);
    }
}