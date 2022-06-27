package r2d.element.action;

import r2d.element.image.ElementImage;
import r2d.render.desktop.Canvas;
import rutil.container.Array;
import rutil.text.Data;
import rwt.device.graphic.Color4f;
import rwt.device.graphic.TextFont;
import rwt.device.input.Input;
import rwt.device.graphic.Draw;
import rwt.device.texture.Texture;

public class ElementHanging extends ElementImage {

    boolean visible;

    Array<String> texts;

    int mx;
    int my;

    TextFont font = TextFont.MICROSOFT;
    Color4f color = Color4f.WHITE;

    public ElementHanging(double x, double y, int width, int height, Texture texture, Array<String> hangEffect) {

        super(x, y, width, height, texture);

        texts = hangEffect;

    }

    private int maxLength;

    public void render() {

        super.render();

        if(visible) {
            int size = Draw.getFontSize();
            Draw.color(Color4f.SHADOW);
            Draw.renderRect(mx, my, maxLength * (size + 1), (texts.size() + 1) * size);

            for(int i = 0; i < texts.size(); i++) {
                Draw.color(color);
                Draw.font(font);
                Draw.renderString(mx + size / 2, my + size * (i + 1), texts.get(i));

                int l = Data.lengthOf(texts.get(i));
                if(l > maxLength) {
                    maxLength = l;
                }
            }
        }

    }

    public void tick(Canvas canvas) {

        visible = isHanging();
        mx = Input.getMouseX();
        my = Input.getMouseY();

    }

    public void setStyle(TextFont f, Color4f c) {

        font = f;
        color = c;

    }

}
