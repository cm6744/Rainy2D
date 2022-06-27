package rutil.container;

import java.util.HashMap;

public class Map<E> {

    private HashMap<String, E> hsb = new HashMap<>();

    public void set(String key, E obj) {

        hsb.put(key, obj);

    }

    public E get(String key) {

        return hsb.get(key);

    }

}
