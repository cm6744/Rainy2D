package r2d.element.vector;

import r2d.element.image.ElementImage;
import r2d.render.desktop.Canvas;
import r2d.vector.Vector;
import rutil.maths.ToolMath;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementVector extends ElementImage {

    protected double speed;
    protected double speedBackup;
    protected double angle;

    boolean canBeRotated;

    public ElementVector(int width, int height, double speed, double angle, Texture texture) {

        super(0, 0, width, height, texture);

        setSpeed(speed);
        setAngle(angle);

    }

    public void tick(Canvas canvas) {

        locate(Vector.vectorX(x, speed, angle), Vector.vectorY(y, speed, angle));
        task();

    }

    public void updateImage() {

        if(canBeRotated && texture != null) {
            cacheTexture(TexSystem.rotate(getBackupTexture(), angle));
        }

    }

    /**
     * 设置是否可以旋转
     * @param canBeRotated 是否可旋转
     * @param updateImage 是否直接更新旋转图片
     */
    public void rotateState(boolean canBeRotated, boolean updateImage) {

        this.canBeRotated = canBeRotated;
        if(updateImage) {
            updateImage();
        }

    }

    private double tarX;
    private double tarY;
    private boolean movingTask;
    private boolean speedTask;

    public boolean moveDown() {

        return !movingTask;

    }

    public void move(double px, double py) {

        tarX = px;
        tarY = py;
        movingTask = checkNeedToMove();

    }

    private boolean checkNeedToMove() {

        double distance = Vector.distanceBetweenAB(tarX, tarY, x, y);

        if(distance < speedBackup * 10) {
            return false;
        }

        return true;

    }

    private void task() {

        if(movingTask) {
            if(checkNeedToMove()) {
                movingTask = true;
                cacheSpeed(speedBackup);
                setAngle(Vector.angleBetweenAB(tarX, tarY, x, y));
            }
            else {
                speedTask = true;
                movingTask = false;
            }
        }

        if(speedTask) {
            cacheSpeed(ToolMath.ofDouble(speed, -speedBackup / 20));
            if(speed <= 0) {
                speedTask = false;
            }
        }

    }

    public void cacheSpeed(double speed) {

        this.speed = speed;

    }

    public void setSpeed(double speed) {

        this.speed = speed;
        speedBackup = speed;

    }

    public void setAngle(double angle) {

        this.angle = angle;
        updateImage();

    }

    public void tranAngle(double ma) {

        setAngle(angle + ma);

    }

    public void tranSpeed(double ms) {

        setSpeed(speed + ms);

    }

    public double getSpeed() {

        return speed;

    }

    public double getAngle() {

        return angle;

    }

}
