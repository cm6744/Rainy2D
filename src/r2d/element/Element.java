package r2d.element;

import r2d.render.desktop.Canvas;
import rutil.container.VarMap;
import rutil.container.Map;
import r2d.logic.Tickable;
import rutil.shape.Circle;
import rutil.shape.Rectangle;
import rwt.device.input.Keys;
import rwt.device.input.Input;
import rwt.device.graphic.Draw;
import rwt.device.texture.Texture;

/**
 * 构造器：offset
 * 需要传入左上角的xy
 */
public class Element extends Tickable {

    public static int DEFAULT_CHECKOUT_OFFSET = 10;

    public boolean forRemove;

    protected double x;
    protected double y;
    protected double offsetX;
    protected double offsetY;
    protected int width;
    protected int height;

    protected Texture texture;
    protected Texture textureBackup;

    protected Circle circle;
    protected Rectangle rect;

    //用于标记对象属性，可以自定义
    public Map<String> texts;
    public VarMap numbers;

    protected boolean notCheckOut;

    public Element(double offsetX, double offsetY, int width, int height, Texture texture) {

        setSize(width, height);//先设置size，因为locate需要size的支持
        locateOffset(offsetX, offsetY);
        setTexture(texture);

        circle = new Circle(0, 0, 0);
        rect = new Rectangle(0, 0, 0, 0);

        texts = new Map<>();
        numbers = new VarMap();

    }

    /**
     * 模板构造器，不传入位置信息，需要后期定位
     */
    public Element(int width, int height, Texture texture) {

        this(0, 0, width, height, texture);

    }

    protected boolean checkCanRender() {

        return texture != null;

    }

    public void render() {

        if(checkCanRender()) {
            Draw.render(offsetX, offsetY, width, height, texture);
        }

    }

    public void tick(Canvas canvas) {}

    //should be "checkOutField".
    public boolean checkOutWindow(Canvas canvas) {

        return checkOutWindow(canvas, DEFAULT_CHECKOUT_OFFSET);

    }

    public boolean checkOutWindow(Canvas canvas, int offset) {

        if(notCheckOut) {
            return false;
        }

        if(offsetX - offset > canvas.right || offsetY - offset > canvas.bottom
                || getX2() + offset < canvas.left || getY2() + offset < canvas.top) {
            return true;
        }

        return false;

    }

    /**
     * 请务必使用locate设置位置！否则不会渲染
     * @param x 中心x
     * @param y 中心y
     */
    public void locate(double x, double y) {

        offsetX = x - width / 2;
        offsetY = y - height / 2;
        this.x = x;
        this.y = y;

    }

    /**
     * 请务必使用locate设置位置！否则不会渲染
     * @param offsetX 左上角x
     * @param offsetY 左上角y
     */
    public void locateOffset(double offsetX, double offsetY) {

        x = offsetX + width / 2;
        y = offsetY + height / 2;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

    }

    /**
     * 已经处理了设置后位置偏移的问题
     */
    public void setSize(int w, int h) {

        width = w;
        height = h;

        offsetX = x - width / 2;
        offsetY = y - height / 2;//提示新的width，更新offset变量

    }

    public void tranSize(int mw, int mh) {

        setSize(width + mw, height + mh);

    }

    public void tranLoc(double mx, double my) {

        locate(x + mx, y + my);

    }

    public void setTexture(Texture texture) {

        this.texture = texture;
        textureBackup = texture;

    }

    //不改变backup，用于重复显示破坏性的图片操作（如亮度）
    public void cacheTexture(Texture texture) {

        this.texture = texture;

    }

    public void setNotCheckOut(boolean ntc) {

        notCheckOut = ntc;

    }

    public double getX() {

        return x;

    }

    public double getY() {

        return y;

    }

    public double getX2() {

        return offsetX + width;

    }

    public double getY2() {

        return offsetY + height;

    }

    public double getOffsetX() {

        return offsetX;

    }

    public double getOffsetY() {

        return offsetY;

    }

    public int getWidth() {

        return width;

    }

    public int getHeight() {

        return height;

    }

    public Texture getTexture() {

        return texture;

    }

    public Texture getBackupTexture() {

        return textureBackup;

    }

    /**
     * 用于子弹判定
     * @return 返回判定半径为：1/2 width的圆形
     */
    public Circle getCircle() {

        circle.locate(x, y);
        circle.setSize(width, height);

        return circle;

    }

    public Rectangle getRect() {

        rect.locateOffset(offsetX, offsetY);
        rect.setSize(width, height);

        return rect;

    }

    public boolean isHanging() {

        int mx = Input.getMouseX();
        int my = Input.getMouseY();

        return mx > offsetX && my > offsetY && mx < getX2() && my < getY2();

    }

    public boolean isClicking() {

        return Input.isMouseClicking(Keys.M_LEFT) && isHanging();

    }

    /**
     * 不检查位置，只检查宽高
     * @see r2d.element.vector.ElementVector
     * 不检查角度和速度，因为它们是可变的
     * @param e
     * @return
     */
    public boolean sameAs(Element e) {

        return width == e.width && height == e.height && texture == e.texture;

    }

    public void callRemove() {

        forRemove = true;

    }

}
