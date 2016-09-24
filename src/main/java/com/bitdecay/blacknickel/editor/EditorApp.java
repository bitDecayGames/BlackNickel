package com.bitdecay.blacknickel.editor;

import com.badlogic.gdx.Game;
import com.bitdecay.blacknickel.MyGame;
import com.bitdecay.blacknickel.screen.GameScreen;
import com.bitdecay.blacknickel.util.RunMode;
import com.bitdecay.jump.leveleditor.render.LevelEditor;

/**
 * This is used in the editor
 */
public class EditorApp extends Game {

    @Override
    public void create() {
        // Change to our room screen
        MyGame game = new MyGame(RunMode.DEV);
        game.create();
        GameScreen screen = new GameScreen(game);
        setScreen(new LevelEditor(screen));
    }

}