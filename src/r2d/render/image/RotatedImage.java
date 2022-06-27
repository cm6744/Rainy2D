package r2d.render.image;

import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

public class RotatedImage {

    Texture texture;

    double angle;

    public RotatedImage(Texture texture) {

        this.texture = texture;

    }

    public Texture getTexture() {

        return TexSystem.rotate(texture, angle);

    }

    public void setAngle(double angle) {

        this.angle = angle;

    }

    public void tranAngle(double ma) {

        angle = angle + ma;

    }

    public double getAngle() {

        return angle;

    }

    public void setImage(Texture texture) {

        this.texture = texture;

    }

}
