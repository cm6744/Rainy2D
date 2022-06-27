package rsc;

import rsc.parse.MethodParser;
import rsc.parse.ValueParser;
import rutil.container.Array;
import rutil.container.Map;
import rutil.text.Parser;
import rwt.resource.Reader;

public class Properties {

    Array<String> props;

    Map<String> values = new Map<>();

    public Properties(String path) {

        props = Reader.read(path);

        for(int i = 0; i < props.size(); i++) {
            checkValue(props.get(i));
        }

    }

    private void checkValue(String prop) {

        if(MethodParser.checkDesc(prop)) {
            return;
        }

        values.set(ValueParser.parseKey(prop), ValueParser.parseStr(prop));//not string, cache as string

    }

    public int intKey(String name) {

        return Parser.parseInt(values.get(name));

    }

    public double doubleKey(String name) {

        return Parser.parseDouble(values.get(name));

    }

    public boolean booleanKey(String name) {

        return Parser.parseBoolean(values.get(name));

    }

    public String stringKey(String name) {

        return values.get(name);

    }

}
