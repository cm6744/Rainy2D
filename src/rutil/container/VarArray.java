package rutil.container;

public class VarArray {

    Array<Double> array = new Array<>();

    public int size(){

        return array.size();

    }

    public int last(){

        return array.last();

    }

    public void add(double value) {

        array.add(value);

    }

    public void set(int index, double value) {

        array.set(index, value);

    }

    public double get(int index){

        Double d = array.get(index);
        return d == null ? 0 : d;

    }
    public void clear(){

        array.clear();

    }

    public void copyFrom(VarArray arr) {

        clear();

        for(int i = 0; i < arr.size(); i++) {
            array.add(arr.get(i));
        }

    }

}
