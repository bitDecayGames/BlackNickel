package com.bitdecay.blacknickel;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bitdecay.blacknickel.util.RunMode;
import com.bitdecay.blacknickel.util.TexturePackerUtils;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * The launcher now reads the program args as well as the lastModified files to determine whether or not to run the texture packer.
 */
public class Launcher {
    private static Logger log = Logger.getLogger(Launcher.class);

    public static Config conf = ConfigFactory.load("conf/application.conf");

    public static void main(String[] args) {
        BasicConfigurator.configure();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Launcher.conf.getInt("resolution.default.width");
        config.height = Launcher.conf.getInt("resolution.default.height");
        config.title = Launcher.conf.getString("title");
        config.resizable = false;

        RunMode runMode = RunMode.PROD;
        if (args != null && args.length > 0) {
            // check for command line arguments
            if (arg(args, "dev")) runMode = RunMode.DEV;
            else if (arg(args, "test")) runMode = RunMode.TEST;
        }
        log.debug("Run Mode: " + runMode);
        if (runMode == RunMode.DEV) TexturePackerUtils.pack();

        new LwjglApplication(new MyGame(runMode), config);
    }

    private static boolean arg(String[] args, String arg){
        for (String cmd : args) if (cmd.equalsIgnoreCase(arg)) return true;
        return false;
    }
}
