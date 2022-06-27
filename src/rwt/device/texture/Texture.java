package rwt.device.texture;

import java.awt.image.*;

public class Texture {

    public BufferedImage data;

    int width;
    int height;

    public Texture(BufferedImage i) {

        data = i;
        width = data.getWidth(null);
        height = data.getHeight(null);

    }

    public BufferedImage source() {

        return data;

    }

    public int getWidth() {

        return width;

    }

    public int getHeight() {

        return height;

    }

}
