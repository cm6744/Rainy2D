package test.stg.screen.attacks.impl.boss;

import r2dx.stg.element.ElementEnemy;
import test.stg.screen.attacks.impl.EnemyActImpl;

public class BossAct1 extends EnemyActImpl {

    public void tick(ElementEnemy e) {

        if(e.forTick(50)) {
            util.ringShoot(e, r.bl00, 0, 2, timer, 24, false, null);
        }

        if(e.forTick(70, 20) && forTick(5)) {
            util.ringShoot(e, r.bl01, 0, 2.5, timer - 45, 64, false, null);
        }

    }

}
