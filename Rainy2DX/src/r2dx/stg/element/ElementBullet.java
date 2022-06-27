package r2dx.stg.element;

import r2d.element.Element;
import r2d.element.vector.ElementVector;
import r2d.render.desktop.Canvas;
import r2dx.stg.action.BulletAct;
import rutil.shape.Circle;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementBullet extends ElementVector {

    /**
     * 两种状态，可用于判定玩家子弹和敌方子弹
     */
    public static final int HIT_PLAYER = 0;
    public static final int HIT_ENEMY = 1;

    private int state;

    boolean grazed;

    int type;
    int cutSize;
    int color;
    private Texture textureAll;
    int boundSize;

    Element parent;
    BulletAct action;

    /**
     * 模板构造器
     */
    public ElementBullet(int width, int height, double speed, double angle, int bound, Texture texture) {

        super(width, height, speed, angle, texture);
        boundSize = bound;

    }

    /**
     * 读取type，可以通过dye染色
     */
    public ElementBullet(int type, int cutSize, int width, int height, double speed, double angle, int bound, Texture texture) {

        super(width, height, speed, angle, texture);

        this.type = type;
        this.cutSize = cutSize;

        textureAll = texture;
        if(textureAll != null) {
            dye(0);
        }

        boundSize = bound;

    }

    public void tick(Canvas canvas) {

        super.tick(canvas);

        if(action != null) {
            if(!action.init) {
                action.init(this);//初始化在此调用
                action.init = true;
            }
            action.update(this);
        }

    }

    public void setAction(BulletAct action) {

        this.action = action;

    }

    public BulletAct getAction() {

        return action;

    }

    public void setParent(Element e) {

        parent = e;

    }

    public Element getParent() {

        return parent;

    }

    public void dye(int color) {

        dye(type, color, cutSize);

    }

    public void dye(int type, int color) {

        dye(type, color, cutSize);

    }

    public void dye(int type, int color, int size) {

        setColor(color);
        setType(type);

        if(size > 0) {
            setTexture(TexSystem.cutType(size, type, color, textureAll));
            updateImage();
        }

    }

    public void setImageAll(Texture texture) {

        textureAll = texture;

    }

    public void setCutSize(int size) {

        cutSize = size;

    }

    public void setType(int type) {

        this.type = type;

    }

    public void setColor(int color) {

        this.color = color;

    }

    public void setBoundSize(int boundSize) {

        this.boundSize = boundSize;

    }

    public void setState(int state) {

        this.state = state;

    }

    public void setGrazed(boolean grazed) {

        this.grazed = grazed;

    }

    public Texture getTextureAll() {

        return textureAll;

    }

    public int getCutSize() {

        return cutSize;

    }

    public int getState() {

        return state;

    }

    public boolean isGrazed() {

        return grazed;

    }

    public int getType() {

        return type;

    }

    public int getColor() {

        return color;

    }

    public int getBoundSize() {

        return boundSize;

    }

    public Circle getCircle() {

        circle.locate(x, y);
        circle.setSize(boundSize, boundSize);

        return circle;

    }

}
