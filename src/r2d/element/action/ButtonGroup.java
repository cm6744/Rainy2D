package r2d.element.action;

import r2d.element.Element;
import r2d.render.desktop.Canvas;
import rutil.container.Array;
import rwt.device.sound.SoundDevice;

public class ButtonGroup extends Element {

    public static int showTickBuffer = 5;

    Array<ElementButton> buttons = new Array<>();

    boolean visible;
    int choose;

    String effect;

    ButtonGroup next;
    ButtonGroup last;

    int showTime;

    public ButtonGroup(String eff) {

        super(0, 0, 0, 0, null);

        effect = eff;

    }

    public void add(ElementButton b) {

        buttons.add(b);
        b.setGroupIn(this);

    }

    public void connect(ButtonGroup last, ButtonGroup next) {

        this.last = last;
        this.next = next;

    }

    public void render() {

        if(visible) {
            for(int i = 0; i < buttons.size(); i++) {
                buttons.get(i).render();
            }
        }

    }

    public void tick(Canvas canvas) {

        if(visible) {
            showTime++;
            ElementButton b;
            for(int i = 0; i < buttons.size(); i++) {
                b = buttons.get(i);
                b.tick(canvas);//after tick, every button's hang is false.
                if(b.isClicking()) {
                    SoundDevice.playRush(effect);
                }
            }
        }
        else {
            showTime = 0;
        }

    }

    public void hide() {

        visible = false;
        choose = 0;

    }

    public void display() {

        visible = true;

    }

    public void gotoNext() {

        if(next != null) {
            next.display();
            hide();
        }

    }

    public void gotoLast() {

        if(last != null) {
            last.display();
            hide();
        }

    }

    public boolean isVisible() {

        return visible;

    }

    public boolean isAvailable() {

        return showTime > showTickBuffer;

    }

}
