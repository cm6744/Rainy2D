package rutil.text;

public class VariableString {

    StringBuilder builder = new StringBuilder();

    public void add(String str) {

        builder.append(str);

    }

    public void add(int str) {

        add(Data.toString(str));

    }

    public void add(double str) {

        add(Data.toString(str));

    }

    public void add(boolean str) {

        add(Parser.booleanTo1or0(str));

    }

    public String create() {

        return builder.toString();

    }

    public void clear() {

        builder.delete(0, length());

    }

    public void remove(int num) {

        if(length() > 0) {
            builder.delete(length() - num, length());
        }

    }

    public int length() {

        return builder.length();

    }

}
