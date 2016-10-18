package com.bitdecay.blacknickel.editor;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.util.TexturePackerUtils;
import com.bitdecay.jump.leveleditor.render.LevelEditor;
import org.apache.log4j.Logger;

/**
 * Launches the editor around the game
 */
public class EditorLauncher {

    private static final Logger log = Logger.getLogger(EditorLauncher.class);

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Launcher.conf.getInt("resolution.editor.width");
        config.height = Launcher.conf.getInt("resolution.editor.height");
        config.title = Launcher.conf.getString("title");
        config.fullscreen = false;


        if (TexturePackerUtils.pack()) {
            log.error("The editor must have an up-to-date packed images file.  Since the packer was just run, you must re-run the editor.");
            System.exit(0);
        }

        LevelEditor.setFileDialogStartingDirectory("src/main/resources/level");
        LevelEditor.setAssetsFolder("../jump/jump-leveleditor/assets");

        new LwjglApplication(new EditorApp(), config);
    }
}