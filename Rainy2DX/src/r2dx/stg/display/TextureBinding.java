package r2dx.stg.display;

import rutil.container.Array;
import rwt.device.texture.Texture;

public class TextureBinding {

    public Array<Integer> uids = new Array<>();
    public Array<Texture> textures = new Array<>();
    public int size;

    public void add(int id, Texture tex) {

        uids.add(id);
        textures.add(tex);
        size++;

    }

}
