package rutil.shape;

public class Rectangle2D extends Rectangle {

    /**
     * 有时候用这种构造方法非常方便呢
     * @param x1 左上角x
     * @param y1 左上角y
     * @param x2 右下角x
     * @param y2 右下角y
     */
    public Rectangle2D(int x1, int y1, int x2, int y2) {

        super(x1, y1, x2 - x1, y2 - y1);

    }

}
