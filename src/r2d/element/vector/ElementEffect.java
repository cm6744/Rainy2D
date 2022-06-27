package r2d.element.vector;

import r2d.render.desktop.Canvas;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

public class ElementEffect extends ElementVector {

    int sizeCrease;
    int leastSize;

    double tranCrease;
    double transparency = 1;

    public ElementEffect(int width, int height, double speed, double angle, Texture texture) {

        super(width, height, speed, angle, texture);

    }

    public void tick(Canvas canvas) {

        super.tick(canvas);

        if(width < leastSize || height < leastSize || transparency <= 0) {
            callRemove();
        }
        else {
            tranSize(sizeCrease, sizeCrease);
            transparency += tranCrease;
        }

        if(transparency > 0 && transparency <= 1) {
            cacheTexture(TexSystem.trans(getBackupTexture(), transparency));
            cacheTexture(TexSystem.rotate(getTexture(), angle));
        }

    }

    public void setSizeCrease(int down) {

        sizeCrease = down;

    }

    public void setLeastSize(int size) {

        leastSize = size;

    }

    public void setTranCrease(double crease) {

        tranCrease = crease;

    }

    public void setTransparency(double tran) {

        transparency = tran;

    }

}
