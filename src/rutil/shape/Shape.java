package rutil.shape;

import rutil.maths.Maths;

public class Shape {

    protected int x;
    protected int y;
    protected int offsetX;
    protected int offsetY;
    protected int width;
    protected int height;

    public void locate(int x, int y) {

        this.x = x;
        this.y = y;
        offsetX = x - width / 2;
        offsetY = y - height / 2;

    }

    public void locate(double x, double y) {

        locate(Maths.toInt(x), Maths.toInt(y));

    }

    public void locateOffset(int offsetX, int offsetY) {

        x = offsetX + width / 2;
        y = offsetY + height / 2;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

    }

    public void locateOffset(double offsetX, double offsetY) {

        locateOffset(Maths.toInt(offsetX), Maths.toInt(offsetY));

    }

    public void setSize(int width, int height) {

        this.width = width;
        this.height = height;

    }

    public void tranSize(int mw, int mh) {

        setSize(width + mw, height + mh);

    }

    public void tranLoc(double mx, double my) {

        locate(x + mx, y + my);

    }

    public int getOffsetX() {

        return offsetX;

    }

    public int getOffsetY() {

        return offsetY;

    }

    public int getWidth() {

        return width;

    }

    public int getHeight() {

        return height;

    }

    /**
     * 比例获取方法
     * @param percent 百分比
     * @return 百分之x的长度
     */
    public int getX(double percent) {

        return offsetX + Maths.toInt(width * percent);

    }

    public int getY(double percent) {

        return offsetY + Maths.toInt(height * percent);

    }

    public int getWidth(double percent) {

        return Maths.toInt(width * percent);

    }

    public int getHeight(double percent) {

        return Maths.toInt(height * percent);

    }

    public int getX2() {

        return offsetX + width;

    }

    public int getY2() {

        return offsetY + height;

    }

    public int getX() {

        return x;

    }

    public int getY() {

        return y;

    }

}
