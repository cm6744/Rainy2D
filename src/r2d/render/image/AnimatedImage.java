package r2d.render.image;

import rutil.container.Array;
import r2d.logic.Tickable;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

public class AnimatedImage extends Tickable {

    int viewTimer;
    int indexOfArray;

    public Array<Texture> texturesL = new Array<>();
    public Array<Texture> texturesR = new Array<>();

    public AnimatedImage(int textureViewTimer) {

        viewTimer = textureViewTimer;

    }

    public void loadFromImage(int wc, int hc, int hi, Texture texture) {

        Texture rt = TexSystem.mirror(texture);

        for(int i = 0; i < wc; i++) {
            texturesL.add(TexSystem.cutTile(wc, hc, i, hi, texture));
            texturesR.add(TexSystem.cutTile(wc, hc, wc - 1 - i, hi, rt));
        }

    }

    public void tick() {

        timer++;

        if(timer % viewTimer == 0) {
            indexOfArray++;

            if(indexOfArray >= texturesL.size()) {
                reset();
            }
        }

    }

    public void reset() {

        indexOfArray = 0;

        super.reset();

    }

    public Texture getLeftTextureNow() {

        return texturesL.get(indexOfArray);

    }

    public Texture getRightTextureNow() {

        return texturesR.get(indexOfArray);

    }

    public int getNowIndex() {

        return indexOfArray;

    }

}
