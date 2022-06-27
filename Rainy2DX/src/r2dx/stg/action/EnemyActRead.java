package r2dx.stg.action;

import r2d.vector.Vector;
import r2dx.stg.CanvasSTG;
import r2dx.stg.element.ElementBullet;
import r2dx.stg.element.ElementEnemy;
import r2dx.stg.element.ElementPlayer;
import rutil.container.Array;
import rutil.maths.Maths;
import rutil.maths.ToolMath;

public class EnemyActRead extends EnemyAct {

    public Array<Actor> actors = new Array<>();
    public ElementPlayer player;

    public EnemyActRead(CanvasSTG c, ElementPlayer p) {

        super(c);
        player = p;

    }

    public void tick(ElementEnemy e) {

        for(int i = 0; i < actors.size(); i++) {
            actors.get(i).tick(e);
        }

    }

    public Actor cra() {

        return new Actor();

    }

    public class Actor {

        public double angleTurn;
        public double speedLimit;
        public double speedTurn;
        public int start;
        public int end = Maths.MAX;
        public int space = 1;
        public int period = 1;
        public double shootX;
        public double shootY;
        public boolean shootPlayer;
        public boolean shootTimer;
        public double shootAngle;
        public double shootSpeed;
        public int shootNumber;
        public int shootSpace;
        public int shootSize;
        public BulletActRead act;
        public ElementBullet bullet;
        public int color;
        public int shootMode;
        public boolean rotated;
        public int offsetRing;

        public void tick(ElementEnemy e) {

            if(e.inTick(start, end) && e.forTick(space, period)) {
                if(angleTurn != 0) {
                    e.tranAngle(angleTurn);
                }
                if(speedTurn != 0) {
                    e.setSpeed(ToolMath.ofDouble(e.getSpeed(), speedTurn, speedLimit));
                }
                double a = shootAngle;
                double x = shootX == 0 ? e.getX() : shootX;
                double y = shootY == 0 ? e.getY() : shootY;
                if(shootPlayer) {
                    a = Vector.angleBetweenAB(player, e) + shootAngle;
                }
                else if(shootTimer) {
                    a = e.timer * a;
                }
                if(bullet != null) {
                    switch(shootMode) {
                        case 0:
                            util.shoot(e, x, y, bullet, color, shootSpeed, a, rotated, act);
                            break;
                        case 1:
                            util.ringShoot(e, x, y, offsetRing, bullet, color, shootSpeed, a, shootNumber, rotated, act);
                            break;
                        case 2:
                            util.direShoot(e, x, y, bullet, color, shootSpeed, a, shootSize, shootSpace, rotated, act);
                            break;
                    }
                }
            }

        }

    }

}
