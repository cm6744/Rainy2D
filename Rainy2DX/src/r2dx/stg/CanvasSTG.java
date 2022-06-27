package r2dx.stg;

import r2d.element.Element;
import r2d.render.desktop.Canvas;
import r2d.render.desktop.Screen;
import r2dx.stg.element.ElementBullet;
import r2dx.stg.element.ElementEnemy;
import r2dx.stg.element.ElementItem;
import r2dx.stg.stage.Stage;
import r2dx.stg.stage.StageUtil;
import rutil.container.Array;
import rutil.shape.Rectangle;
import rwt.device.graphic.Draw;

public class CanvasSTG extends Canvas {

    public Array<ElementBullet> bullets = new Array<>(5000);
    public Array<ElementEnemy> enemies = new Array<>(1000);
    public Array<ElementItem> items = new Array<>(1000);

    public StageUtil util;
    public Stage stage;

    public Array<Stage> stages = new Array<>();
    public int nowStage;
    public boolean boundDisplayOn;

    public CanvasSTG(Screen scr) {

        super(scr);

        util = new StageUtil(this);

        setRepaintField(new Rectangle(155, 30, 460, 540));//default stg field
        setDefaultSize(800, 600);

        util.updateField();

    }

    //回调订阅方法，用于自定义检测
    public void tick(ElementEnemy e, ElementBullet b) {}

    public void tick(ElementItem e) {}

    public void update() {

        if(!isPause) {
            vectorTick(bullets);
            vectorTick(enemies);
            vectorTick(items);
            boundTick();

            if(stage != null) {
                if(!stage.init) {
                    stage.init();//init stages then tick
                    stage.init = true;
                }

                stage.tick();

                if(!util.isDialogShowing()) {
                    stage.callTimer();//timer stages if conversation isn't showing, but canvas will still tick.
                }
            }
        }

        super.update();

    }

    public void cheatRender(Array<? extends Element> elements) {

        Element e;
        int size = elements.size();

        for(int i = size; i >= 0; i--) {
            e = elements.get(i);
            if(e != null) {
                e.render();
                Draw.color3f(55, 255, 200);
                Draw.renderCircle(e.getCircle());
            }
        }

    }

    public void renderObjects() {

        defaultRender(items);
        defaultRender(enemies);

        if(!boundDisplayOn) {
            defaultRender(bullets);
        }
        else {
            cheatRender(bullets);
        }

    }

    //init方法
    public void addStage(Stage s) {

        stages.add(s);
        stage = stages.get(0);//add is in init() so stage must be at pos(0)

    }

    public void nextStage() {

        nowStage++;
        stage = stages.get(nowStage);//update stage var

        util.clear(false, -1);

        stage.init();

    }

    private void boundTick() {

        int sizeE = enemies.size();
        int sizeB = bullets.size();
        ElementEnemy e;
        ElementBullet b;

        //双遍历进行基本碰撞逻辑
        for(int i = sizeE; i >= 0; i--) {
            e = enemies.get(i);
            if(e != null) {
                for(int j = sizeB; j >= 0; j--) {
                       b = bullets.get(j);
                       if(b != null) {
                           tick(e, b);//遍历到的结果传入，复写此方法操作即可
                       }
                }
            }
        }

    }

}
