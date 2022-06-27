package test.stg.screen.attacks.impl.boss;

import r2dx.stg.element.ElementEnemy;
import rutil.maths.Maths;
import test.stg.screen.attacks.impl.EnemyActImpl;

public class BossAct2 extends EnemyActImpl {

    public void tick(ElementEnemy e) {

        if(e.forTick(1)) {
            util.ringShoot(e, r.bl01, 5, Maths.random(1.5, 3), Maths.random(0, 360), 3, false, null);
        }

    }

}
