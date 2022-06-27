package test.func;

import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.eval.Scope;
import parsii.eval.Variable;
import parsii.tokenizer.ParseException;
import r2d.element.action.ElementButtonText;
import r2d.element.action.ElementTextField;
import r2d.render.desktop.Canvas;
import r2d.render.desktop.Screen;
import rutil.container.Array;
import rutil.maths.ToolMath;
import rutil.text.Data;
import rwt.device.graphic.Color4f;
import rwt.device.graphic.TextFont;
import rwt.device.input.Input;
import rwt.device.input.Keys;
import rwt.device.graphic.Draw;

public class CanvasFunc extends Canvas {

    public int speedRoll = 50;
    public double speedPer = 1;
    Array<String> funcs = new Array<>();

    double percent = 10;
    double xl;
    double yl;

    public CanvasFunc(Screen scr) {

        super(scr);

        setDefaultSize(800, 600);
        xl = bufferWidth / 2;
        yl = bufferHeight / 2;

    }

    ElementButtonText rollUp = new ElementButtonText(30, 570, 40, 40, "Rs+");
    ElementButtonText rollDown = new ElementButtonText(80, 570, 40, 40, "Rs-");
    ElementButtonText perUp = new ElementButtonText(130, 570, 40, 40, "Per+");
    ElementButtonText perDown = new ElementButtonText(180, 570, 40, 40, "Per-");
    ElementButtonText remove = new ElementButtonText(540, 570, 40, 40, "Clear");
    ElementTextField field = new ElementTextField(640, 570, 150, 20);

    public void init() {

        buttons.add(rollUp);
        buttons.add(rollDown);
        buttons.add(perUp);
        buttons.add(perDown);
        buttons.add(remove);
        imageAbove.add(field);

    }

    public void onPause() {

        setPause(false);

    }

    public void tick() {

        setPause(false);

        if(rollUp.isClicking()) speedRoll++;
        if(rollDown.isClicking()) speedRoll--;
        if(perUp.isClicking()) speedPer += 0.1;
        if(perDown.isClicking()) speedPer -= 0.1;
        if(remove.isClicking()) funcs.clear();

        if(!field.isActive()) {
            if(Input.isKeyDown(Keys.Z)) {
                percent = ToolMath.ofDouble(percent, speedPer, 50);
            }

            if(Input.isKeyDown(Keys.X)) {
                percent = ToolMath.ofDouble(percent, -speedPer, 0.5);
            }

            if(Input.isKeyDown(Keys.LEFT)) {
                xl += speedRoll / percent;
            }

            if(Input.isKeyDown(Keys.RIGHT)) {
                xl -= speedRoll / percent;
            }

            if(Input.isKeyDown(Keys.UP)) {
                yl += speedRoll / percent;
            }

            if(Input.isKeyDown(Keys.DOWN)) {
                yl -= speedRoll / percent;
            }
        }

        if(Input.isKeyPress(Keys.ENTER)) {
            String text = field.getText();
            if(!Data.isEmpty(text)) {
                funcs.add(text);
                field.clear();
            }
        }

    }

    Variable v;
    Expression exp;
    int nowi;

    private void cache(int index) {

        nowi = index;
        String fn = funcs.get(index);
        Scope scope = new Scope();
        v = scope.getVariable("x");
        try {
            exp = Parser.parse(fn, scope);
        } catch(ParseException e) {}

    }

    public double yfxFunc(double x) {

        double y;

        if(v == null || exp == null) {
            funcs.remove(nowi);
            return 0;
        }
        v.setValue(x);
        y = exp.evaluate();
        return y;

    }

    public void renderAboveField() {

        Draw.color(Color4f.WHITE);

        Draw.font(TextFont.MICROSOFT.resize(12));
        Draw.renderString(0, 10, Data.toString(screen.nowFpsRender));
        Draw.renderString(0, 30, Data.toString(screen.nowFpsUpdate));

        Draw.renderString(10, 520, "RollSpeed: " + Data.toString(speedRoll));
        Draw.renderString(10, 535, "PerSpeed: " + Data.toString(speedPer));

        Draw.font(TextFont.MICROSOFT.resize(15));
        Draw.renderString(field.getOffsetX() - 35, field.getOffsetY() - 18, "Add a new function here!");
        Draw.renderString(field.getOffsetX() - 30, field.getOffsetY(), "y = ");
        Draw.font(TextFont.MICROSOFT.resize(12));
        Draw.renderStringCenter(bufferWidth / 2, 7, "Z/X to zoom, Direction Keys to move");

        Draw.font(TextFont.MICROSOFT.resize(8));
        Draw.color(Color4f.LIGHT);

        //-xl is the screen left
        //i + xl is render loc
        double ip = percent > 50 ? percent : 50;

        double xlp = xl % ip;
        double ylp = yl % ip;

        for(int i = 0; i < bufferWidth; i += ip) {
            Draw.renderRect(i + xlp, yl - 4, 1, 4);
            Draw.renderString(i + xlp, yl - 12, Data.toString((i - xl + xlp) / percent, 1));
        }

        for(int i = 0; i < bufferHeight; i += ip) {
            Draw.renderRect(xl, i + ylp, 4, 1);
            Draw.renderString(xl + 5, i + ylp, Data.toString(-(i - yl + ylp) / percent, 1));
        }

        Draw.renderRect(xl - 1, 0, 2, bufferHeight);
        Draw.renderRect(0, yl - 1, bufferWidth, 2);
        Draw.renderRect(xl - 2, yl - 2, 4, 4);

        for(int k = 0; k < funcs.size(); k++) {
            cache(k);
            for(double i = 0; i < bufferWidth; i += 0.1) {
                double x = i - xl;
                double y = yfxFunc(x / percent);
                double ry = yl - y * percent;
                if(ry > 0 && ry < bufferHeight)
                Draw.renderCircleSize(i, ry, 1);
            }
        }

    }

}
