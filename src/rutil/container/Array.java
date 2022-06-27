package rutil.container;

public class Array<E> {

    public Object[] objects;
    private int size;

    private static int DEFAULT_CAPACITY = 50;

    public Array(int capacity){

        objects = new Object[capacity];

    }

    public Array(){

        this(DEFAULT_CAPACITY);

    }

    public static void arrayCopy(Object[] oldArray, Object[] newArray, int oldStartPos, int newStartPos, int copyLength) {

        if(newStartPos + copyLength < newArray.length) {
            for(int i = 0; i < copyLength; ++i) {
                newArray[i + newStartPos] = oldArray[i + oldStartPos];
            }
        }

    }

    public void checkRangeNeedIncrease(int index, Object obj) {

        if(size >= objects.length){
            Object[] newObjects = new Object[size * 2];

            if(index == -1 && obj == null) {
                Array.arrayCopy(objects, newObjects, 0, 0, size);
            }
            else {
                Array.arrayCopy(objects, newObjects, index, index + 1, size - index);
            }

            objects = newObjects;
        }

    }

    public boolean checkIndex(int index){

        if(index >= 0 && index < size){
            return true;
        }

        return false;

    }

    public int size(){

        return size;

    }

    public int last(){

        return size - 1;

    }

    /**
     * @return 数组可存长度，批量添加使用
     */
    public int length() {

        return objects.length;

    }

    public int indexOf(E obj) {

        if(obj != null) {
            for(int i = size; i >= 0; i--) {
                if(obj.equals(objects[i])) {
                    return i;
                }
            }
        }

        return -1;

    }

    public void add(E obj){

        checkRangeNeedIncrease(-1, null);
        objects[size++] = obj;

    }

    public void set(int index, E obj) {

        checkRangeNeedIncrease(index, null);
        objects[index] = obj;
        if(size - 1 < index) {
            size = index + 1;
        }

    }

    public E get(int index){

        if(checkIndex(index)) {
            return (E) objects[index];
        }

        return null;

    }

    public E getLast() {

        return get(last());

    }

    public void clear(){

        for(int i = 0; i < size; i++) {
            objects[i] = null;
        }

        size = 0;

    }

    public E remove(int index) {

        if(checkIndex(index)) {
            Object obj = objects[index];

            if(index == size){
                objects[index] = null;
                return (E) obj;
            }
            else {
                Array.arrayCopy(objects, objects, index + 1, index, size - index);
            }
            size--;
            return (E) obj;
        }

        return null;

    }

    public E remove(E obj){

        return remove(indexOf(obj));

    }

    public boolean contains(E obj) {

        return indexOf(obj) >= 0;

    }

    public void copyFrom(Array<E> arr) {

        clear();

        size = arr.size();
        objects = new Object[arr.length()];

        for(int i = 0; i < arr.size; i++) {
            objects[i] = arr.get(i);
        }

    }

    public Object[] toDefaultArray() {

        Object[] out = new Object[size];

        arrayCopy(objects, out, 0, 0, size);

        return out;

    }

    /**
     * example:
     * [0, 1, 2, 3, 4, 5, 6] size = 7;
     * cut(start = 2, end = 7) ->
     * [2, 3, 4, 5, 6]
     */
    public Array<E> cut(int start, int end) {

        Array<E> n = new Array<>();

        for(int i = start; i < end; i++) {
            n.add(get(i));
        }

        return n;

    }

}
