package r2dx.stg.action;

import r2dx.stg.CanvasSTG;
import r2dx.stg.element.ElementEnemy;
import r2dx.stg.stage.Stage;
import r2dx.stg.stage.StageUtil;
import r2d.logic.Tickable;
import rutil.maths.Maths;
import rutil.maths.Random;

/**
 * 使用本类时先继承出MyAttack，构造函数传入自己的canvas，然后再继承，就能使用自己的资源和stage了
 */
public class EnemyAct extends Tickable {

    protected Random ran = Maths.sec;
    public boolean init;

    public CanvasSTG canvas;
    public StageUtil util;
    public Stage stage;

    //请不要在重复使用的action上修改这个，除非你每次都new一个！
    public int maxTime = Maths.MAX;
    public boolean isEnd;

    public EnemyAct(CanvasSTG c) {

        canvas = c;

        stage = canvas.stage;
        util = canvas.util;

    }

    public void update(ElementEnemy e) {

        isEnd = timer > maxTime;

        if(!isEnd) {
            tick(e);
            callTimer();
        }

    }

    public void init(ElementEnemy e) {}

    public void tick(ElementEnemy e) {}

    public void remove(ElementEnemy e) {}

}
