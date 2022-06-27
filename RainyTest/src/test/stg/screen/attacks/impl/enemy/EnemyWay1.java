package test.stg.screen.attacks.impl.enemy;

import r2d.vector.Vector;
import r2dx.stg.element.ElementEnemy;
import test.stg.screen.attacks.impl.EnemyActImpl;

public class EnemyWay1 extends EnemyActImpl {

    public void tick(ElementEnemy e) {

        if(e.forTick(12)) {
            util.ringShoot(e, r.bl02, 1, 1.5, Vector.angleBetweenAB(player, e), 1, true, null);
            util.ringShoot(e, r.bl02, 2, 2.5, timer, 8, true, null);
        }

    }

}
