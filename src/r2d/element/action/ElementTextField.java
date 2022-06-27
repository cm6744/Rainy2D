package r2d.element.action;

import r2d.element.Element;
import r2d.render.desktop.Canvas;
import rutil.shape.Rectangle;
import rutil.text.Data;
import rwt.device.graphic.Color4f;
import rwt.device.graphic.TextFont;
import rwt.device.input.Input;
import rwt.device.input.Keys;
import rwt.device.input.TextInputParser;
import rwt.device.graphic.Draw;

public class ElementTextField extends Element {

    TextInputParser parser;

    TextFont font = TextFont.MICROSOFT;
    Color4f color = Color4f.WHITE;

    public ElementTextField(double x, double y, int width, int height) {

        super(x, y, width, height, null);

        parser = new TextInputParser();

    }

    public void render() {

        Rectangle r = getRect();

        Draw.color(Color4f.SHADOW);
        Draw.renderRect(r);
        Draw.color(Color4f.LIGHT);
        Draw.renderFrame(r, 1);

        Draw.font(font);
        Draw.color(color);

        Draw.renderString(offsetX + 5, offsetY, parser.getText());

        if(parser.canParse() && forTick(10, 5)) {
            Draw.color(Color4f.LIGHT);
            Draw.renderFrame(r, 1);
        }

    }

    public void tick(Canvas canvas) {

        if(Input.isMouseClicking(Keys.M_LEFT)) {
            parser.setCanParse(isHanging());
        }

        parser.parse();

    }

    public void setText(String str) {

        parser.setText(str);

    }

    public String getText() {

        return parser.getText();

    }

    //return enter send message from parser.
    public String getSend() {

        return Input.isKeyPress(Keys.ENTER) ? getText() : Data.BLANK;

    }

    public void clear() {

        parser.clear();

    }

    public boolean isActive() {

        return parser.canParse();

    }

    public void setActive(boolean can) {

        parser.setCanParse(can);

    }

    public void setStyle(TextFont f, Color4f c) {

        font = f;
        color = c;

    }

}
