package r2dx.stg.stage;

import r2dx.stg.CanvasSTG;
import r2dx.stg.element.ElementBoss;
import r2d.logic.Tickable;

/**
 * 这里的逻辑写的太乱了
 * 大致顺序是这样：
 * 主画布继承StageCanvas，自动建立6个stage的数组，建立1个StageUtil
 * 数组首先指向0，util也永远指向StageCanvas的util，util不需要自己初始化，stage需要手动add
 *
 * stage可以处理逻辑而不必开新画布，重新声明资源
 */
public class Stage extends Tickable {

    public StageUtil util;
    public CanvasSTG canvas;

    public boolean isEnd;
    public boolean init;

    public Stage(CanvasSTG c) {

        canvas = c;

        util = canvas.util;

    }

    public void init() {}

    public void tick() {}

    public void render() {}

    //订阅方法，只在boss开始攻击调用一次
    public void bossStart(ElementBoss boss) {}

    //订阅方法，只在boss死亡调用一次
    public void bossDie(ElementBoss boss) {}

}
