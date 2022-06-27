package test.stg.screen.stages;

import r2d.render.image.DisplayImage;
import r2d.vector.Vector;
import r2dx.stg.display.Dialog;
import r2dx.stg.element.ElementBoss;
import r2dx.stg.factory.ActFactory;
import r2dx.stg.factory.DialogFactory;
import r2dx.stg.stage.Stage;
import r2d.logic.WaitTimer;
import rutil.maths.Maths;
import rutil.shape.Direction;
import rwt.device.sound.SoundDevice;
import rwt.resource.Reader;
import test.stg.screen.InGameCanvas;
import test.stg.screen.attacks.impl.boss.BossAct1;
import test.stg.screen.attacks.impl.boss.BossAct2;
import test.stg.screen.attacks.impl.enemy.EnemyWay1;

public class Stage1 extends Stage {

    InGameCanvas igc;
    Resources r;

    Dialog cb;
    Dialog ca;

    DisplayImage stg6;
    WaitTimer endW = new WaitTimer();

    public Stage1(InGameCanvas sc) {

        super(sc);

        igc = sc;
        r = Resources.INSTANCE;

    }

    public void init() {

        SoundDevice.playLoop(Reader.sound("sounds/th08_13"));

        ActFactory.canvas = canvas;
        ActFactory.player = igc.player;
        ActFactory.bm = r.bulletMap;

        igc.bgManager.layersBackground(
                Reader.image("textures/picture/background/BG4-1"),
                null//Reader.image("textures/picture/background/BG4-1E"),
        ,2,
                3
        );

        r.boss.actions.add(new BossAct2());
        r.boss.actions.add(new BossAct1());

        cb = DialogFactory.create(igc, "scripts/dialog/con1");
        ca = DialogFactory.create(igc, "scripts/dialog/con1");

        stg6 = new DisplayImage(r.stg6Img, 150, 200, 20);

    }

    int ed = Direction.LEFT;

    public void tick() {

        if(forTick(200) && inTick(0, 100)) {
            ed = Direction.opposite(ed);
            double a = Vector.opposite(Direction.angleOf(ed));
            util.addEnemy(r.enemy, 0.1, 3, a, ed, new EnemyWay1());
            util.addEnemy(r.enemy, 0.2, 3, a,  ed, new EnemyWay1());
            util.addEnemy(r.enemy, 0.3, 3, a,  ed, new EnemyWay1());
        }
        else if(timer > 100 && util.bossNotEnd(r.boss)) {
            util.bossTick(r.boss, cb, ca, 100, 500, 380, 130, 300, -100);
        }
        else if(inTick(500, 19200)) {
            if(forTick(100)) {
                util.addEnemy(r.enemy, Maths.random(0.2, 0.8), 2, Maths.random(95, 85), Direction.UP, new EnemyWay1());
            }
        }
        else if(timer > 19200 && util.bossNotEnd(r.boss6)) {
            util.bossTick(r.boss6, cb, ca, 100, -100, 380, 130, 300, -100);
            endW.wait(200);
        }

        endW.update();

    }

    public void render() {

        if(stg6 != null)
        stg6.render(timer);

    }

    public void bossStart(ElementBoss boss) {

        if(boss == r.boss6) {
            SoundDevice.playLoop(Reader.sound("sounds/th08_14"));
        }
        else {
            //igc.bgManager.slowBackground(Reader.image("textures/picture/background/BG4-5"), 0.5);
        }

    }

    public void bossDie(ElementBoss boss) {

        SoundDevice.playRush("sounds/effect/boss_die");

    }

}
