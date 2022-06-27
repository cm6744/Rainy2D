package rwt.resource;

import rutil.text.Data;

import java.io.*;

public class ResourceFinder {

    public static File jarFile(String path, String format) {

        String out = System.getProperty("basepath");
        if(Data.isEmpty(out)) {
            out = ResourceFinder.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        }

        String par = new File(out).getParentFile().getPath();
        String end = (par + "/" + path + format);

        return new File(end);

    }

    public static InputStream readerJar(String path, String format) {

        try {
            return new FileInputStream(jarFile(path, format));
        }
        catch(FileNotFoundException e) {}

        return null;

    }

    public static FileWriter writerJar(String path, String format) {

        try {
            return new FileWriter(jarFile(path, format));
        }
        catch(IOException e) {}

        return null;

    }

}
