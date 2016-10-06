package com.bitdecay.blacknickel.editor;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.util.TexturePackerUtils;
import com.bitdecay.jump.leveleditor.render.LevelEditor;
import org.apache.log4j.BasicConfigurator;

/**
 * Launches the editor around the game
 */
public class EditorLauncher {

    public static void main(String[] arg) {
        BasicConfigurator.configure();

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Launcher.conf.getInt("resolution.editor.width");
        config.height = Launcher.conf.getInt("resolution.editor.height");
        config.title = Launcher.conf.getString("title");
        config.fullscreen = false;

        TexturePackerUtils.pack();

        LevelEditor.setFileDialogStartingDirectory("src/main/resources/level");
        LevelEditor.setAssetsFolder("../jump/jump-leveleditor/assets");

        new LwjglApplication(new EditorApp(), config);
    }
}