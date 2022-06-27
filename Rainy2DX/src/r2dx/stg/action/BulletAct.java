package r2dx.stg.action;

import r2dx.stg.CanvasSTG;
import r2dx.stg.element.ElementBullet;
import r2dx.stg.stage.Stage;
import r2dx.stg.stage.StageUtil;
import r2d.logic.Tickable;
import rutil.maths.Maths;
import rutil.maths.Random;

public class BulletAct extends Tickable {

    protected Random ran = Maths.sec;
    public boolean init;

    public CanvasSTG canvas;
    public StageUtil util;
    public Stage stage;

    //请不要在重复使用的action上修改这个，除非你每次都new一个！
    public int maxTime = Maths.MAX;
    public boolean isEnd;

    public BulletAct(CanvasSTG c) {

        canvas = c;

        stage = canvas.stage;
        util = canvas.util;

    }

    public void update(ElementBullet b) {

        isEnd = timer > maxTime;

        if(!isEnd) {
            tick(b);
            callTimer();
        }

    }

    public void init(ElementBullet b) {}

    public void tick(ElementBullet b) {}

    public void remove(ElementBullet b) {}

}
