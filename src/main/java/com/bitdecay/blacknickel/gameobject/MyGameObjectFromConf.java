package com.bitdecay.blacknickel.gameobject;

import com.bitdecay.blacknickel.Launcher;
import com.bitdecay.blacknickel.component.AbstractComponent;
import com.bitdecay.blacknickel.component.IconComponent;
import com.bitdecay.blacknickel.component.NameComponent;
import com.bitdecay.blacknickel.component.PositionComponent;
import com.typesafe.config.Config;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Parse the config files for game objects and initialize them here
 */
public final class MyGameObjectFromConf {

    private static Config gobsConf = Launcher.conf.getConfig("gobs");
    private static List<Config> defaultConf = gobsConf.getConfigList("default").stream().map(Config.class::cast).collect(Collectors.toList());
    private static List<Config> listConf = gobsConf.getConfigList("list").stream().map(Config.class::cast).collect(Collectors.toList());

    private MyGameObjectFromConf(){}

    public static List<Config> objectConfs(){
        List<Config> list = new ArrayList<>();
        list.addAll(listConf);
        return list;
    }

    public static MyGameObject objectFromConf(String name, float x, float y){
        Optional<Config> conf = configForObjectName(name);
        if (! conf.isPresent()) err("(object: " + name + ") Could not find conf");
        MyGameObject obj = new MyGameObject();
        new NameComponent(obj, name).addSelfToGameObject();
        new PositionComponent(obj, x, y).addSelfToGameObject();
        new IconComponent(obj, conf.map(config -> {
            if (config.hasPath("icon")) return config.getString("icon");
            else {
                err("(object: " + name + ") icon is missing from conf");
                return null;
            }
        }).get()).addSelfToGameObject();
        List<Config> componentsList = componentConfigListForConfig(conf);
        componentsList.forEach(componentConf -> componentFromConf(name, obj, componentConf).addSelfToGameObject());
        obj.cleanup();
        return obj;
    }

    public static AbstractComponent componentFromConf(MyGameObject obj, Config conf){
        return componentFromConf("unknown", obj, conf);
    }

    private static AbstractComponent componentFromConf(String objName, MyGameObject obj, Config conf){
        String componentName = conf.getString("name");
        String className = "com.bitdecay.blacknickel.component." + componentName + "Component";
        try {
            Class componentClass = Class.forName(className);
            try {
                Constructor<? extends AbstractComponent> componentConstructorWithConf = componentClass.getConstructor(MyGameObject.class, Config.class);
                return componentConstructorWithConf.newInstance(obj, conf);
            }  catch (NoSuchMethodException a) {
                try {
                    Constructor<? extends AbstractComponent> componentConstructor = componentClass.getConstructor(MyGameObject.class);
                    return componentConstructor.newInstance(obj);
                } catch (NoSuchMethodException b) {
                    err("(object: " + objName + ") Could not construct component with name: " + className + " (Tip: look in the component class, there must be a constructor that takes only a MyGameObject, or a constructor that takes a MyGameObject and a Config)");
                }
            }
        } catch (InstantiationException e){
            err("(object: " + objName + ") There was a problem with the constructor of: " + className + " (is the class abstract?)");
        } catch (ClassNotFoundException e) {
            err("(object: " + objName + ") Could not find class with name: " + className);
        } catch (InvocationTargetException e){
            err("(object: " + objName + ") There was a problem creating " + className + " (Tip: your conf file is probably missing a key:value or the key is misspelled)", e.getCause());
        } catch (Exception e){
            err("(object: " + objName + ") General exception with " + className, e);
        }
        // should never get this far...
        return null;
    }

    private static Optional<Config> configForObjectName(String name){
        return listConf.stream().filter(c -> c.getString("name").equalsIgnoreCase(name) || (c.hasPath("aliases") && c.getStringList("aliases").stream().filter(s -> s.equalsIgnoreCase(name)).findFirst().isPresent())).findFirst();
    }

    private static List<Config> componentConfigListForConfig(Optional<Config> conf){
        List<Config> components = componentConfigListForConfigRecursive(conf);
        doExtend(components, defaultConf);
        return components;
    }

    private static List<Config> componentConfigListForConfigRecursive(Optional<Config> confOpt){
        return confOpt.map(conf -> {
            List<Config> components = conf.getConfigList("components").stream().map(Config.class::cast).collect(Collectors.toList());
            if (conf.hasPath("extends")) doExtend(components, componentConfigListForConfigRecursive(configForObjectName(conf.getString("extends"))));
            return components;
        }).orElse(Collections.emptyList());
    }

    private static void doExtend(List<Config> original, List<Config> extended){
        List<String> componentNames = original.stream().map(config -> config.getString("name")).collect(Collectors.toList());
        extended.forEach(extendedConf -> {
            String extendedName = extendedConf.getString("name");
            // do not overwrite with extended values
            if (!componentNames.contains(extendedName)) original.add(extendedConf);
        });
    }

    private static void err(String msg, Throwable cause){
        throw new RuntimeException("ConfToGameObj: " + msg, cause);
    }

    private static void err(String msg){
        throw new RuntimeException("ConfToGameObj: " + msg);
    }
}
