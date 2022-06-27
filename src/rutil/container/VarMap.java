package rutil.container;

import rutil.maths.Maths;

public class VarMap {

    Map<Double> map = new Map<>();

    public void set(String key, double value) {

        map.set(key, value);

    }

    public double get(String key) {

        Double d = map.get(key);
        return d == null ? 0 : d;

    }

    public int getAsInt(String key) {

        return Maths.toInt(get(key));

    }

    public void translate(String key, double value) {

       set(key, get(key) + value);

    }

}
