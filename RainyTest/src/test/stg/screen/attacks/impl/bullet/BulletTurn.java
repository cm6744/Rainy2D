package test.stg.screen.attacks.impl.bullet;

import r2dx.stg.element.ElementBullet;
import test.stg.screen.attacks.impl.BulletActImpl;

public class BulletTurn extends BulletActImpl {

    public double ta;
    public double ts;
    public int period;

    public static BulletTurn create(double a, double s, int p) {

        BulletTurn turn = new BulletTurn();
        turn.ta = a;
        turn.ts = s;
        turn.period = p;
        return turn;

    }

    public void tick(ElementBullet b) {

        if(forTick(period)) {
            b.tranAngle(ta);
            b.tranSpeed(ts);
        }

    }

}
