package rwt.device.graphic;

import java.awt.*;

public class TextFont {

    public static TextFont MICROSOFT = new TextFont("微软雅黑", 16);

    /////////*********////////

    public Font data;

    String name;
    int size;

    public TextFont(String n, int s) {

        name = n;
        size = s;

        data = new Font(name, Font.PLAIN, size);

    }

    public TextFont resize(int size) {

        return new TextFont(name, size);

    }

    public String getName() {

        return name;

    }

    public int getSize() {

        return size;

    }

}
