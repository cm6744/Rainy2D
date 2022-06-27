package r2dx.stg.action;

import r2dx.stg.CanvasSTG;
import r2dx.stg.element.ElementPlayer;
import r2dx.stg.stage.StageUtil;
import r2d.logic.Tickable;
import rutil.maths.Maths;
import rutil.maths.Random;

public class PlayerAction extends Tickable {

    protected Random ran = Maths.sec;
    public CanvasSTG canvas;
    public StageUtil util;

    public PlayerAction(CanvasSTG c) {

        canvas = c;
        util = canvas.util;

    }

    public void tick(ElementPlayer player) {}

    public void callBomb() {}

}
