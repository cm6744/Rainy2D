package r2d.render.image;

import r2d.element.image.ElementImage;
import rwt.device.texture.TexSystem;

//用于显示一个淡入淡出的图片
public class DisplayImage {

    ElementImage stageName;

    int startTime;
    int endTime;
    int effectTime;

    /**
     * @param stn 元素，包括位置大小
     * @param start 开始tick
     * @param end 结束tick
     * @param effect 淡入淡出持续tick
     */
    public DisplayImage(ElementImage stn, int start, int end, int effect) {

        stageName = stn;
        startTime = start;
        endTime = end;
        effectTime = effect;

    }

    public void render(double timer) {

        if(timer > startTime && timer < endTime) {
            double trans = 1;

            if(timer < startTime + effectTime) {
                trans = (timer - startTime) / effectTime;
            }
            else if(timer > endTime - effectTime) {
                trans = (endTime - timer) / effectTime;
            }
            stageName.cacheTexture(TexSystem.trans(stageName.getBackupTexture(), trans));
            stageName.render();
        }

    }

    public boolean isEnd(int timer) {

        return timer > endTime;

    }

}
