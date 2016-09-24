package com.bitdecay.blacknickel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.bitdecay.blacknickel.screen.GameScreen;
import com.bitdecay.blacknickel.screen.SplashScreen;
import com.bitdecay.blacknickel.trait.ICanSetScreen;
import com.bitdecay.blacknickel.util.RunMode;
import com.bytebreakstudios.animagic.texture.AnimagicTextureAtlas;
import com.bytebreakstudios.animagic.texture.AnimagicTextureAtlasLoader;

/**
 * The actual game object used by libgdx.
 */
public class MyGame extends Game implements ICanSetScreen{
    // statics
    public static AssetManager ASSET_MANAGER = new AssetManager();
    public static AnimagicTextureAtlas ATLAS;
    public static RunMode RUN_MODE;


    public MyGame(RunMode runMode){
        super();
        MyGame.RUN_MODE = runMode;
    }

    public void queueAssetsForLoad() {
        ASSET_MANAGER.setLoader(AnimagicTextureAtlas.class, new AnimagicTextureAtlasLoader(new InternalFileHandleResolver()));
        ASSET_MANAGER.load("img/packed/main.atlas", AnimagicTextureAtlas.class);
    }

    @Override
    public void create() {
        queueAssetsForLoad();
        ASSET_MANAGER.finishLoading();
        ATLAS = ASSET_MANAGER.get("img/packed/main.atlas", AnimagicTextureAtlas.class);

        if (RUN_MODE == RunMode.DEV) setScreen(new GameScreen(this));
        else if (RUN_MODE == RunMode.PROD) setScreen(new SplashScreen(this));
    }
}
