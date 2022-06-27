package r2dx.stg.action;

import r2d.vector.Vector;
import r2dx.stg.CanvasSTG;
import r2dx.stg.element.ElementBullet;
import r2dx.stg.element.ElementPlayer;
import rutil.container.Array;
import rutil.maths.Maths;
import rutil.maths.ToolMath;

public class BulletActRead extends BulletAct {

    public Array<Actor> actors = new Array<>();
    public ElementPlayer player;

    public BulletActRead(CanvasSTG c, ElementPlayer p) {

        super(c);
        player = p;

    }

    public void tick(ElementBullet b) {

        for(int i = 0; i < actors.size(); i++) {
            actors.get(i).tick(b);
        }

    }

    public Actor cra() {

        return new Actor();

    }

    public class Actor {

        public boolean turnPlayer;
        public double angleTurn;
        public double speedLimit;
        public double speedTurn;
        public int start;
        public int end = Maths.MAX;
        public int space = 1;
        public int period = 1;

        public void tick(ElementBullet b) {

            if(b.inTick(start, end) && b.forTick(space, period)) {
                if(turnPlayer) {
                    double a = Vector.angleBetweenAB(player, b);
                    b.tranAngle(a > b.getAngle() ? angleTurn : -angleTurn);
                }
                else if(angleTurn != 0) {
                    b.tranAngle(angleTurn);
                }

                if(speedTurn != 0) {
                    b.setSpeed(ToolMath.ofDouble(b.getSpeed(), speedTurn, speedLimit));
                }
            }

        }

    }

}
