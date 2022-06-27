package r2d.element.image;

import r2d.element.Element;
import rwt.device.texture.Texture;

/**
 * 构造器：inset
 * 超类：offset
 * 实现了以中心点来实现一切活动
 * 高级类都继承于它
 */
public class ElementImage extends Element {

    public ElementImage(double x, double y, int width, int height, Texture texture) {

        super(x - width / 2, y - height / 2, width, height, texture);

    }

    public ElementImage(int width, int height, Texture texture) {

        super(- width / 2, - height / 2, width, height, texture);

    }

}
