package r2d.logic;

public class Tickable {

    public static double MILL = 0.006;
    public static int SECOND = 60;
    public static int MINUTE = 3600;

    public int fullTimer;
    public int timer;
    public WaitTimer waiter = new WaitTimer();

    public boolean forTick(int tick) {

        return forTick(tick, 1);

    }

    public boolean forTick(int tick, int period) {

        if(tick > 0) {
            return timer % tick < period;
        }

        return true;

    }

    public boolean inTick(int start, int end) {

        return timer > start && timer < end;

    }

    public void callTimer() {

        timer++;
        fullTimer++;
        waiter.update();

    }

    public void reset() {

        timer = 0;
        waiter.forceBack();

    }

}
