package rwt.device.graphic;

import rutil.container.Array;
import rutil.maths.Maths;
import rutil.text.Parser;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

public class NumberFont {

    Array<Texture> numbers = new Array<>(10);

    Texture fontsAll;

    boolean zeroFilling;
    int sizeOfNumber;

    public NumberFont(Texture fonts, boolean fillWithZero, int size) {

        fontsAll = fonts;
        zeroFilling = fillWithZero;
        sizeOfNumber = size;

    }

    public void tick(int number) {

        cutNum(number);

    }

    public void render(double x, double y, int eachWidth, int height) {

        for(int i = 0; i < numbers.size(); i++) {
            Draw.render(x + (i * eachWidth), y, eachWidth, height, numbers.get(i));
        }

    }

    private void addNumber(int num) {

        numbers.add(TexSystem.cutLine(10, num, fontsAll));

    }

    private void cutNum(int num) {

        numbers.clear();

        int length = Maths.sizeOf(num);

        if(length < sizeOfNumber && zeroFilling) {
            for(int i = 0; i < sizeOfNumber - length; i++) {
                addNumber(0);//将位数不足的用0补满
            }
        }

        for(int i = 0; i < length; i++) {
            addNumber(Parser.intAt(num, i));
        }

    }

}
