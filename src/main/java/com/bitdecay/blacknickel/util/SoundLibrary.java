package com.bitdecay.blacknickel.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.bitdecay.blacknickel.Launcher;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigObject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * SoundLibrary is now fully built and configured based on the location of files and the sounds.conf file.  Look at the /resources/conf/sounds.conf file to figure out how to change the volume for each individual sound effect and music.  If you don't enter a value into sounds.conf, a default value will be used.
 */
public class SoundLibrary {
    private static final Logger log = Logger.getLogger(SoundLibrary.class);

    private static final String fxDir = Launcher.conf.getString("sounds.fxDir");
    private static final float defaultFxVolume = (float) Launcher.conf.getDouble("sounds.defaultFxVolume");

    private static final String musicDir = Launcher.conf.getString("sounds.musicDir");
    private static final float defaultMusicVolume = (float) Launcher.conf.getDouble("sounds.defaultMusicVolume");

    private static final HashMap<String, SoundEffect> sounds = new HashMap<>();
    private static final HashMap<String, MusicEffect> musics = new HashMap<>();


    static {
        // loop through the defined sounds and adjust their volume
        loadSounds("sounds.fx", defaultFxVolume, (name, volume) -> sounds.put(name, new SoundEffect(volume)));
        // loop through the defined musics and adjust their volume
        loadSounds("sounds.music", defaultMusicVolume, (name, volume) -> musics.put(name, new MusicEffect(volume)));
    }

    private static void loadSounds(String confLocation, float defaultVolume, BiConsumer<String, Float> func){
        ConfigList conf = Launcher.conf.getList(confLocation);
        conf.forEach(configValue -> {
            if (configValue instanceof ConfigObject){
                Config sound = ((ConfigObject) configValue).toConfig();
                String name = sound.getString("name");
                float volume = defaultVolume;
                if (sound.hasPath("volume")) volume = (float) sound.getDouble("volume");
                log.debug("Loaded sound: " + confLocation + "." + name + "(" + volume + ")");
                func.accept(name, volume);
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
        try {
            SoundEffect sound;

            sound = sounds.get(name);
            if (sound == null) throw new RuntimeException("Could not find configured sound: " + name + ".  You must define it in the sounds.conf file.");
            if (sound.sound == null) {
                sound.sound = Gdx.audio.newSound(Gdx.files.classpath("sound/fx/" + name + ".wav"));
            }

            return sound;
        } catch (Exception e){
            throw new RuntimeException("Could not get sound: " + name, e);
        }
    }

    public static synchronized Music playMusic(String name) {
        return getMusic(name).play();
    }

    public static synchronized Music loopMusic(String name) {
        return getMusic(name).loop();
    }


    private static MusicEffect getMusic(String name) {
        try {
            MusicEffect music;

            music = musics.get(name);
            if (music == null) throw new RuntimeException("Could not find configured music: " + name + ". You must define it in the sounds.conf file.");
            if (music.music == null) {
                FileHandle musicFile = Gdx.files.classpath("sound/music/" + name + ".mp3");
                if (!musicFile.exists()) musicFile = Gdx.files.classpath("sound/music/" + name + ".wav");
                music.music = Gdx.audio.newMusic(musicFile);
                musics.put(name, music);
            }

            return music;
        } catch (Exception e){
            throw new RuntimeException("Could not get music: " + name, e);
        }
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
