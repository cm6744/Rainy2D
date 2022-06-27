package r2d.logic;

public class WaitTimer {

    boolean isWaiting;
    int waitTime;
    int waitTimer;

    public void update() {

        waitTimer++;
        if(waitTimer >= waitTime) {
            isWaiting = false;
        }

    }

    public void wait(int time) {

        if(isWaitBack()) {
            waitTimer = 0;
            waitTime = time;
            isWaiting = true;
        }

    }

    public boolean isWaitBack() {

        return !isWaiting;

    }

    public void forceBack() {

        waitTimer = waitTime;

    }

    public int getWaitTimeLeft() {

        return waitTime - waitTimer;

    }

}
