package r2d.render.desktop;

import r2d.element.Element;
import r2d.element.action.ElementButton;
import r2d.element.vector.ElementEffect;
import r2d.render.manager.BackgroundManager;
import r2d.render.manager.MouseManager;
import r2d.render.manager.ShadowManager;
import rutil.container.Array;
import r2d.logic.Tickable;
import rutil.maths.Maths;
import rutil.shape.Rectangle;
import rwt.device.input.Input;
import rwt.device.input.Keys;
import rwt.device.render.DisplayDevice;
import rwt.device.render.RawDraw;
import rwt.device.graphic.Draw;
import rwt.device.texture.Texture;

public class Canvas extends Tickable {

    public int C_LEFT;
    public int C_TOP;
    public int C_WIDTH;
    public int C_HEIGHT;
    public int WI_WIDTH;
    public int WI_HEIGHT;

    /**
     * 一些内部变量，如果你不非常了解，请不要乱动！
     * bufferWidth:缓冲图像宽
     * bufferHeight:缓冲图像高
     * totalWidth:实际显示宽度（保持比例）= C_WIDTH
     * totalHeight：实际显示高度 = C_HEIGHT
     * l/t/r/b:field四周坐标
     */
    public int bufferWidth;
    public int bufferHeight;
    int totalWidth;
    int totalHeight;

    public double overPercent;

    public int left;
    public int top;
    public int right;
    public int bottom;

    /**
     * 缓存SC变量
     */
    public int leftBuffer;
    public int topBuffer;

    Texture overFieldImage;

    public Rectangle field;
    public Screen screen;

    /**
     * cycle在[0-1]以0.01的速度循环（正弦曲线）
     * cyclePM[-0.5-0.5]
     */
    public double cycle;
    public double cyclePM;

    public boolean init;
    /**
     * Pause暂停的原理：
     * 暂停元素tick和画布tick
     * 如果复写其他的方法不会被暂停！！
     */
    public boolean isPause;

    public Array<Element> imageBottom = new Array<>(50);
    public Array<Element> imageMiddle = new Array<>(50);
    public Array<Element> imageFront = new Array<>(50);
    public Array<Element> imageAbove = new Array<>(50);
    public Array<ElementButton> buttons = new Array<>(50);
    public Array<ElementEffect> effects = new Array<>(15000);
    public Array<Element> hangings = new Array<>(100);

    public BackgroundManager bgManager;
    public MouseManager mouseManager;

    public ShadowManager shadow;
    private Canvas nextCanvas;

    private boolean quaking;
    private int quakeTimer;
    private int quakeTime;
    private int quakeForce;

    public Canvas(Screen scr) {

        screen = scr;

        bgManager = new BackgroundManager(this);
        shadow = new ShadowManager(this);
        mouseManager = new MouseManager(this);

        setDefaultSize(800, 600);
        setRepaintField(new Rectangle(0, 0, bufferWidth, bufferHeight));

    }

    /**
     * 设置分辨率（默认800x600）
     */
    public void setDefaultSize(int width, int height) {

        WI_WIDTH = DisplayDevice.getWindowWidth();
        WI_HEIGHT = DisplayDevice.getWindowHeight();//refresh

        bufferWidth = width;
        bufferHeight = height;
        overPercent =  Maths.toDouble(WI_HEIGHT) / bufferHeight;
        totalHeight = Maths.toInt(height * overPercent);
        totalWidth = Maths.toInt(width * overPercent);//计算显示宽高比例

        C_WIDTH = totalWidth;
        C_HEIGHT = totalHeight;
        C_LEFT = (WI_WIDTH - C_WIDTH) / 2;
        C_TOP = (WI_HEIGHT - C_HEIGHT) / 2;//设置SC参数

        leftBuffer = C_LEFT;
        topBuffer = C_TOP;//屏幕震动缓存

        Input.option(overPercent, C_LEFT, C_TOP);

    }

    public void setRepaintField(Rectangle rect) {

        field = rect;

        left = field.getOffsetX();
        top = field.getOffsetY();
        right = field.getX2();
        bottom = field.getY2();

        bgManager.updateField();

    }

    public void setImageOverField(Texture texture) {

        overFieldImage = texture;

    }

    public void init() {}

    private void cycleTime() {

        callTimer();

        cycle = Maths.sinEase(timer, 15);
        cyclePM = Maths.sinCycle(timer, 15);

    }

    private void bufferPaint() {

        //将缓冲图片绘制到screen上
        RawDraw.bufferFlush(C_LEFT, C_TOP, C_WIDTH, C_HEIGHT);

    }

    private void renderFrame() {

        if(overFieldImage != null) {
            Draw.render(0, 0, bufferWidth, bufferHeight, overFieldImage);
        }

    }

    /**
     * 初始化显示后，依次绘制到显存上
     * 再调用显存的show方法显示
     * 组件都需要在[0, 0, bufferWidth, bufferHeight]范围内放置
     */
    public void render() {

        bgManager.render();
        defaultRender(imageBottom);
        renderBottomField();//CUSTOM

        defaultRender(imageMiddle);
        renderMiddleField();//CUSTOM

        renderObjects();//EXTENDS to custom your game objects' renderer
        defaultRender(effects);

        defaultRender(imageFront);
        renderFrontField();//CUSTOM

        renderFrame();

        defaultRender(buttons);

        defaultRender(imageAbove);
        renderAboveField();//CUSTOM
        defaultRender(hangings);

        shadow.render();
        mouseManager.renderMouse();

        bufferPaint();

    }

    public void update() {

        if (!init) {
            init();
            init = true;
        }

        checkPause();
        mouseManager.checkMouse();

        if(!isPause) {
            bgManager.tick();
            shadowTick();
            tick();
            defaultTick(imageBottom);
            defaultTick(imageMiddle);
            defaultTick(imageFront);
            defaultTick(imageAbove);
            defaultTick(buttons);
            vectorTick(effects);
            defaultTick(hangings);
            cycleTime();
            quakeTick();
        }
        else {
            pauseTick();
        }

        allTick();

    }

    public void endPause() {}

    public void onPause() {}

    //暂停时逻辑
    public void pauseTick() {}

    //主逻辑
    public void tick() {}

    //always tick whenever pause or not
    public void allTick() {}

    public void renderBottomField() {}

    public void renderMiddleField() {}

    public void renderFrontField() {}

    public void renderAboveField() {}

    public void renderObjects() {}

    public void defaultRender(Array<? extends Element> elements) {

        Element e;
        int size = elements.size();

        for(int i = size; i >= 0; i--) {
            e = elements.get(i);
            if(e != null) {
                e.render();
            }
        }

    }

    public void defaultTick(Array<? extends Element> elements) {

        Element e;
        int size = elements.size();

        for (int i = size; i >= 0; i--) {
            e = elements.get(i);
            if(e != null) {
                e.tick(this);
                e.callTimer();
            }
        }

    }

    public void vectorTick(Array<? extends Element> elements) {

        Element e;
        int size = elements.size();

        for (int i = size; i >= 0; i--) {
            e = elements.get(i);
            if(e != null) {
                e.tick(this);
                e.callTimer();
                if(e.checkOutWindow(this) || e.forRemove) {
                    elements.remove(i);
                }
            }
        }

    }

    public void shadowTick() {

        shadow.tick();

        if(shadow.inEnd()) {
            shadow.clear();

            if(nextCanvas != null) {
                nextCanvas.shadow.blackOut();
                screen.loadFromCanvas(nextCanvas);
                nextCanvas = null;
            }
        }

    }

    //震动背景
    public void quake(int ticks, int force) {

        quaking = true;
        quakeTime = ticks;
        quakeForce = force;

    }

    public void nextCanvas(Canvas next) {

        if(next != null) {
            nextCanvas = next;
            shadow.blackIn();
        }

    }

    private void oneTimeQuake(int force) {

        C_LEFT = leftBuffer + Maths.toInt(Maths.random(-force, force));
        C_TOP = topBuffer + Maths.toInt(Maths.random(-force, force));

    }

    public void resetLocation() {

        C_LEFT = leftBuffer;
        C_TOP = topBuffer;

    }

    private void quakeTick() {

        if(quaking) {
            oneTimeQuake(quakeForce);
            quakeTimer++;
            if(quakeTimer >= quakeTime) {
                quakeTimer = 0;
                quaking = false;
                resetLocation();
            }
        }

    }

    public void checkPause() {

        if(Input.isKeyPress(Keys.ESC)) {
            setPause(!isPause);
        }

    }

    public void setPause(boolean pause) {

        isPause = pause;

        if(pause) {
            onPause();//if is not pause, init
        }
        else {
            endPause();
        }

    }

}
