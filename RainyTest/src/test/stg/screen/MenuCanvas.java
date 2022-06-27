package test.stg.screen;

import r2d.element.Element;
import r2d.element.action.*;
import r2d.element.vector.ElementEffect;
import r2d.render.desktop.Screen;
import r2d.vector.Vector;
import r2dx.stg.CanvasSTG;
import r2dx.stg.stage.Stage;
import rutil.bsck.Platform;
import rutil.container.Array;
import rutil.maths.Maths;
import rutil.shape.Rectangle;
import rwt.device.graphic.Color4f;
import rwt.device.input.Keys;
import rwt.device.input.Input;
import rwt.device.sound.SoundDevice;
import rwt.device.graphic.Draw;
import rwt.device.texture.Texture;
import rwt.resource.Reader;
import test.stg.GameTest;
import test.stg.screen.stages.Resources;

public class MenuCanvas extends CanvasSTG {

    public Element BG = new Element
            (0, 0, bufferWidth, bufferHeight, Reader.image("textures/picture/background/title00"));

    public Element title = new Element
            (256, 0, 512, 256, Reader.image("textures/picture/title02"));

    ElementButton b = new ElementButtonText(100, 320, 128, 32, "Start", "开始新游戏");
    ElementButton b2 = new ElementButtonText(120, 370, 128, 32, "Direct Start", "直接开始游戏（默认难度）");
    ElementButton b3 = new ElementButtonText(140, 420, 128, 32, "Exit", "退出");

    ElementButton easy = new ElementButtonText(100, 320, 128, 32, "Easy", "谁都可以通过的难度");
    ElementButton normal = new ElementButtonText(120, 370, 128, 32, "Normal", "有一些水平的难度");
    ElementButton hard = new ElementButtonText(140, 420, 128, 32, "Hard", "挑战自我的难度");
    ElementButton imp = new ElementButtonText(160, 470, 128, 32, "Impossible", "还是别玩了");

    String eff = "sounds/effect/shoot";

    ButtonGroup g1 = new ButtonGroup(eff);
    ButtonGroup g2 = new ButtonGroup(eff);

    Texture snow = Reader.image("textures/effect/snow");

    Array<ButtonGroup> groups = new Array<>();

    public MenuCanvas(Screen scr) {

        super(scr);

        setRepaintField(new Rectangle(0, 0, bufferWidth, bufferHeight));
        setDefaultSize(800, 600);

    }

    public void init() {

        SoundDevice.playLoop(Reader.sound("sounds/th07_01"));

        imageMiddle.add(title);
        imageBottom.add(BG);

        g1.display();
        g1.connect(null, g2);
        g2.connect(g1, null);

        groups.add(g1);
        groups.add(g2);

        g1.add(b);
        g1.add(b2);
        g1.add(b3);

        g2.add(easy);
        g2.add(normal);
        g2.add(hard);
        g2.add(imp);

        imageMiddle.add(g1);
        imageMiddle.add(g2);

        addStage(new Stage(this));
        //util.setMainLoopMusic(new String("03title"));

        hangings.add(new ElementHanging(50, 50, 50, 50, Resources.INSTANCE.enemyDie, Reader.read("texts/hangings/info")));
        imageAbove.add(tf);

    }

    ElementTextField tf = new ElementTextField(50, 50, 200, 24);

    public void renderAboveField() {

        if(shadow.shadowing()) {
            Draw.render(50, 540, GameTest.loading);
        }

        Draw.renderFontNumber(0, 570, 16, 24, screen.nowFpsUpdate, Resources.INSTANCE.nfrNo);
        Draw.renderFontNumber(60, 570, 16, 24, screen.nowFpsRender, Resources.INSTANCE.nfrNo);
        Draw.color(Color4f.WHITE.retrans(Maths.toInt(cycle * 0.5 + 0.5)));
        Draw.renderCircle(Input.getMouseX(), Input.getMouseY(), 2);

        Draw.color(Color4f.LIGHT);
        Draw.font("Segoe MethodParser", 16);
        Draw.renderString(400, 570, "Press ↑↓ to choose, Z to click, X to return");
        Draw.renderString(400, 590, "Made by cm233, in 2022");

        //Draw.defaultFont();
        //Draw.renderString(620 + Maths.sinCycle(timer, 100) * 20, 430, dialog.getNow());

    }

    int now;

    public void tick() {

        util.addEffect(Maths.toInt(Maths.random(0, 1000)), 0, Maths.random(0.5, 3), Maths.random(130, 140), Maths.toInt(Maths.random(15, 25)), 0, 0, -0.005, snow);

        if(b.isClicking()) {
            g1.gotoNext();
            now++;
        }
        if(b2.isClicking()) {
            GameTest.igc = new InGameCanvas(screen);
            nextCanvas(GameTest.igc);
        }
        if(b3.isClicking()) {
            Platform.exit();
        }
        if(easy.isClicking()) {
            GameTest.igc = new InGameCanvas(screen);
            nextCanvas(GameTest.igc);
        }
        if(normal.isClicking()) {
            GameTest.igc = new InGameCanvas(screen);
            nextCanvas(GameTest.igc);
        }
        if(hard.isClicking()) {
            GameTest.igc = new InGameCanvas(screen);
            nextCanvas(GameTest.igc);
        }
        if(imp.isClicking()) {
            GameTest.igc = new InGameCanvas(screen);
            nextCanvas(GameTest.igc);
        }
        if(Input.isKeyPress(Keys.X)) {
            if(groups.get(now - 1) != null) {
                groups.get(now).gotoLast();
                now--;
            }
        }

        for(int i = 0; i < effects.size(); i++) {
            if(effects.get(i) != null) {
                effectMove(effects.get(i));
            }
        }

    }

    public void effectMove(ElementEffect e) {

        if(Input.isMouseClicking(1)
                && Vector.distanceBetweenAB(e.getX(), e.getY(), Input.getMouseX(), Input.getMouseY()) < 50
                && e.getSpeed() > 0) {
            e.setSpeed(Maths.random(2, 5));
            e.setAngle(Vector.angleBetweenAB(e.getX(), e.getY(), Input.getMouseX(), Input.getMouseY()));
        }
        if(e.timer > 1000) {
            e.callRemove();
        }

    }

}
