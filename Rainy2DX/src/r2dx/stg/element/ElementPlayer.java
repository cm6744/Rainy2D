package r2dx.stg.element;

import r2d.element.image.ElementImage;
import r2d.element.vector.ElementMove;
import r2d.render.desktop.Canvas;
import r2d.vector.Vector;
import r2dx.stg.action.PlayerAction;
import rutil.maths.ToolMath;
import rutil.shape.Circle;
import rwt.device.input.Input;
import rwt.device.input.Keys;
import rwt.device.texture.Texture;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementPlayer extends ElementMove {

    double speedSlow;

    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;

    public boolean shoot;//z down
    public boolean slow;//shift down
    public boolean sure;//z press
    public boolean auto;//c press
    public boolean bomb;//x press

    public int zTime;

    int timeNoHit;

    private ElementImage playerPoint;
    private Circle pointCircle;

    private int BIGGEST_POINT_SIZE = 14;
    private int pointSize = 0;

    public boolean replaying;
    public int replaySerial;

    public PlayerAction action;

    public ElementPlayer(int width, int height, double speed, int serial, Texture texture, Texture point) {

        super(width, height, speed, 0, texture);

        speedSlow = speed / 2;

        setAngle(90);

        playerPoint = new ElementImage(0, 0, 0, 0, point);
        pointCircle = new Circle(0, 0, 0);

        replaySerial =serial;

    }

    public void render() {

        if(timeNoHit <= 0) {
            super.render();
        }
        else if(forTick(5, 4) && timeNoHit > 0) {
            super.render();
            timeNoHit--;
        }

    }

    private boolean pointInit;

    public void tick(Canvas canvas) {

        if(!replaying) {
            playerControl();
        }
        checkAngle();
        animate();

        if(up || left || down || right) {
            locate(Vector.vectorX(x, speed, angle), Vector.vectorY(y, speed, angle));
        }
        if(shoot) {
            zTime++;
        }
        else {
            zTime = 0;
        }

        if(slow) {
            speed = speedSlow;
            pointSize = ToolMath.ofInt(pointSize, 1, BIGGEST_POINT_SIZE);
        }
        else {
            speed = speedBackup;
            pointSize = ToolMath.ofInt(pointSize, -1);
        }

        checkOutField(canvas);

        if(action != null) {
            action.tick(this);
            action.callTimer();
        }

        playerPoint.locate(x, y);
        playerPoint.setSize(pointSize, pointSize);

        if(!pointInit) {
            canvas.imageFront.add(playerPoint);
            pointInit = true;
        }

    }

    public void checkOutField(Canvas canvas) {

        int offset = BIGGEST_POINT_SIZE / 2;
        
        if(x < canvas.left + offset) {
            locate(canvas.left + offset, y);
        }

        if(y < canvas.top + offset) {
            locate(x, canvas.top + offset);
        }

        if(x > canvas.right - offset) {
            locate(canvas.right - offset, y);
        }

        if(y > canvas.bottom - offset) {
            locate(x, canvas.bottom - offset);
        }

    }
    
    public void checkAngle() {

        setAngle(-90);

        if(up && right) {
            setAngle(315);
        }
        else if(up && left) {
            setAngle(225);
        }
        else if(down && left) {
            setAngle(135);
        }
        else if(down && right) {
            setAngle(45);
        }
        else if(up) {
            setAngle(270);
        }
        else if(left) {
            setAngle(180);
        }
        else if(down) {
            setAngle(90);
        }
        else if(right) {
            setAngle(0);
        }
        
    }

    public void playerControl() {

        left = Input.isKeyDown(Keys.LEFT);
        right = Input.isKeyDown(Keys.RIGHT);
        up = Input.isKeyDown(Keys.UP);
        down = Input.isKeyDown(Keys.DOWN);
        slow = Input.isKeyDown(Keys.SHIFT);

        sure = Input.isKeyPress(Keys.Z);
        bomb = Input.isKeyPress(Keys.X);
        shoot = Input.isKeyDown(Keys.Z);

        if(Input.isKeyPress(Keys.C)) {
            auto = !auto;
        }

    }

    public void hit(int tickNoHit) {

        timeNoHit = tickNoHit;
        waiter.wait(tickNoHit);

    }

    public Circle getPointCircle() {

        pointCircle.locate(x, y);
        pointCircle.setSize(0, 0);//just a point

        return pointCircle;

    }

}
