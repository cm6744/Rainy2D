package rwt.resource;

import rutil.container.Array;
import rwt.device.sound.Sound;
import rwt.device.texture.Texture;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.io.*;

public class Reader {

    public static String textureFormat = ".png";
    public static String textFormat = ".txt";
    public static String soundFormat = ".wav";
    public static String textCharset = "UTF-8";

    public static Texture image(String path) {

        try {
            return new Texture(ImageIO.read(ResourceFinder.jarFile(path, textureFormat)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static Array<String> read(String path) {

        Array<String> texts = new Array<>();
        String temp;

        InputStream is;

        try {
            is = ResourceFinder.readerJar(path, textFormat);
            InputStreamReader isr = new InputStreamReader(is, textCharset);
            BufferedReader br = new BufferedReader(isr);

            while((temp = br.readLine()) != null) {
                texts.add(temp);
            }

            br.close();//close buffer first, or throw a IOException
            is.close();
            isr.close();

            return texts;
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return new Array<>();

    }

    public static void write(Array<String> texts, String path) {

        try {
            FileWriter fr = ResourceFinder.writerJar(path, textFormat);
            BufferedWriter bw = new BufferedWriter(fr);

            for(int i = 0; i < texts.size(); i++) {
                bw.write(texts.get(i));
                bw.newLine();
            }

            bw.close();//close buffer first, or throw a IOException
            fr.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static Sound sound(String path) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(ResourceFinder.jarFile(path, soundFormat));

            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            ais.close();

            Sound sound = new Sound();
            sound.set(clip);

            return sound;
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;

    }

}
