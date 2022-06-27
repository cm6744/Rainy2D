package r2d.element.action;

import r2d.element.Element;
import rutil.maths.Maths;
import rwt.device.graphic.Color4f;
import rwt.device.graphic.Draw;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

public class ElementProgressBar extends Element {

    int value;
    int maxValue;
    int spikes;
    int spw;

    double percent;

    public ElementProgressBar(double offsetX, double offsetY, int width, int height, int maxValue, Texture texture) {

        super(offsetX, offsetY, width, height, texture);

        this.maxValue = maxValue;

    }

    public void setSpikes(int num, int width) {

        spikes = num;
        spw = width;

    }

    public void render() {

        Draw.render(offsetX, offsetY, Maths.toInt(percent * width), height, TexSystem.cutP(percent, 1, getBackupTexture()));

        //不渲染第0个，i从1开始
        for(int i = 1; i < spikes; i++) {
            int pos = i * (width / spikes);
            if(pos + spw < percent * width) {//检测spike位置在值宽度范围内
                Draw.color(Color4f.SHADOW);
                Draw.renderRect(offsetX + i * (width / spikes), offsetY, spw, height);
            }
        }

    }

    public void setValue(int value) {

        this.value = value;
        percent = Maths.toDouble(value) / maxValue;

    }

    public void setMaxValue(int maxValue) {

        this.maxValue = maxValue;
        percent = Maths.toDouble(value) / maxValue;

    }

}
