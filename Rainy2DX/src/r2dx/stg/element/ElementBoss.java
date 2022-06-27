package r2dx.stg.element;

import rwt.device.texture.Texture;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementBoss extends ElementEnemy {

    public boolean located;
    public boolean started;
    public boolean ended;
    public boolean in;
    public boolean out;

    /**
     * 模板构造器
     */
    public ElementBoss(int width, int height, double speed, double angle, int startHealth, Texture texture) {

        super(width, height, speed, angle, startHealth, texture);

    }

    public boolean isBoss() {

        return true;

    }

}
