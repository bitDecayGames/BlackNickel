package com.bitdecay.blacknickel.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * This class loads resources that are contained within the jar.  I think it can be replaced with Gdx.files.classpath... maybe...
 */
public final class Resources {
    private Resources(){}

    public static String getString(String path){
        InputStream is = ClassLoader.getSystemResourceAsStream(path);
        String s = convertStreamToString(is);
        try {
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return s;
    }

    private static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is);
        s.useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
