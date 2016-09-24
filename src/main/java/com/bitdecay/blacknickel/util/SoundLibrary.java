package com.bitdecay.blacknickel.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.bitdecay.blacknickel.Launcher;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigObject;

import java.io.File;
import java.util.HashMap;

/**
 * SoundLibrary is now fully built and configured based on the location of files and the sounds.conf file.  Look at the /resources/conf/sounds.conf file to figure out how to change the volume for each individual sound effect and music.  If you don't enter a value into sounds.conf, a default value will be used.
 */
public class SoundLibrary {
    private static final HashMap<String, SoundEffect> sounds = new HashMap<>();
    private static final HashMap<String, MusicEffect> musics = new HashMap<>();

    static {
        // ///////////////////////////
        // Sound Effects
        // ///////////////////////////
        File fxDir = new File(Launcher.conf.getString("sounds.fxDir"));
        float defaultFxVolume = (float) Launcher.conf.getDouble("sounds.defaultFxVolume");
        // add all the fx files with a default volume
        if (fxDir.isDirectory() && fxDir.exists()) for (File fxFile : fxDir.listFiles()) {
            if (fxFile != null && fxFile.exists()){
                sounds.put(new FileHandle(fxFile).nameWithoutExtension(), new SoundEffect(defaultFxVolume));
            }
        }
        // now loop through the defined sounds and adjust their volume
        ConfigList fxs = Launcher.conf.getList("sounds.fx");
        fxs.forEach(configValue -> {
            if (configValue instanceof ConfigObject){
                Config fx = ((ConfigObject) configValue).toConfig();
                SoundEffect fxObj = getSound(fx.getString("name"));
                if (fxObj != null) fxObj.volume = (float) fx.getDouble("volume");
            }
        });

        // ///////////////////////////
        // Music
        // ///////////////////////////
        File musicDir = new File(Launcher.conf.getString("sounds.musicDir"));
        float defaultMusicVolume = (float) Launcher.conf.getDouble("sounds.defaultMusicVolume");
        // add all the music files with a default volume
        if (musicDir.isDirectory() && musicDir.exists()) for (File musicFile : musicDir.listFiles()) {
            if (musicFile != null && musicFile.exists()){
                musics.put(new FileHandle(musicFile).nameWithoutExtension(), new MusicEffect(defaultMusicVolume));
            }
        }
        // now loop through the defined sounds and adjust their volume
        ConfigList musicsConf = Launcher.conf.getList("sounds.music");
        musicsConf.forEach(configValue -> {
            if (configValue instanceof ConfigObject){
                Config music = ((ConfigObject) configValue).toConfig();
                MusicEffect musicObj = getMusic(music.getString("name"));
                if (musicObj != null) musicObj.volume = (float) music.getDouble("volume");
            }
        });
    }

    public static synchronized Sound playSound(String name) {
        return getSound(name).play();
    }

    public static synchronized Sound stopSound(String name) {
        return getSound(name).stop();
    }

    private static SoundEffect getSound(String name) {
        SoundEffect sound;

        sound = sounds.get(name);
        if (sound.sound == null) {
            sound.sound = Gdx.audio.newSound(Gdx.files.classpath("sound/fx/" + name + ".wav"));
        }

        return sound;
    }

    public static synchronized Music playMusic(String name) {
        return getMusic(name).play();
    }

    public static synchronized Music loopMusic(String name) {
        return getMusic(name).loop();
    }


    private static MusicEffect getMusic(String name) {
        MusicEffect music;

        music = musics.get(name);
        if (music.music == null) {
            FileHandle musicFile = Gdx.files.classpath("sound/music/" + name + ".mp3");
            if (!musicFile.exists()) {
                musicFile = Gdx.files.classpath("sound/music/" + name + ".wav");
            }
            music.music = Gdx.audio.newMusic(musicFile);
            musics.put(name, music);
        }

        return music;
    }

    public static void stopMusic(String name) {
        getMusic(name).music.stop();
    }


    private static class SoundEffect {
        public Sound sound;
        public float volume;

        public SoundEffect(Sound sound, float volume) {
            this.sound = sound;
            this.volume = volume;
        }

        public SoundEffect(float volume) {
            this.volume = volume;
        }

        public Sound play() {
            this.sound.play(this.volume);
            return this.sound;
        }

        public Sound stop() {
            this.sound.stop();
            return this.sound;
        }

        public String toString(){
            return "Volume=" + this.volume;
        }
    }

    private static class MusicEffect {
        public Music music;
        public float volume;

        public MusicEffect(Music music, float volume) {
            this.music = music;
            this.volume = volume;
        }

        public MusicEffect(float volume) {
            this.volume = volume;
        }

        public Music play() {
            this.music.play();
            this.music.setVolume(this.volume);
            return this.music;
        }

        public Music loop() {
            this.music.play();
            this.music.setVolume(this.volume);
            this.music.setLooping(true);
            return this.music;
        }

        public String toString(){
            return "Volume=" + this.volume;
        }
    }
}
