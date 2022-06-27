package r2d.element.vector;

import rwt.device.graphic.Color4f;
import rwt.device.graphic.Draw;

public class ElementEffectShape extends ElementEffect {

    public ElementEffectShape(int width, int height, double speed, double angle) {

        super(width, height, speed, angle, null);

    }

    public void render() {

        Draw.color(Color4f.WHITE.retrans(transparency));
        Draw.renderCircle(x, y, width);

    }

}
