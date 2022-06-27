package rutil.shape;

public class Rectangle extends Shape {

    public Rectangle(int offsetX, int offsetY, int width, int height) {

        x = offsetX + width / 2;
        y = offsetY + height / 2;

        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

    }

    public boolean intersects(Rectangle rect) {

        int width1 = width;
        int height1 = height;
        int width2 = rect.width;
        int height2 = rect.height;
        int x1 = offsetX;
        int y1 = offsetY;
        int x2 = rect.offsetX;
        int y2 = rect.offsetY;

        width2 += x2;
        height2 += y2;
        width1 += x1;
        height1 += y1;

        return ((width2 < x2 || width2 > x1)
                && (height2 < y2 || height2 > y1)
                && (width1 < x1 || width1 > x2)
                && (height1 < y1 || height1 > y2));

    }

}
