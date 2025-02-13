package pepse.util;

/**
 * class for represnting a three type object
 *
 * @param <T> first type
 * @param <U> second type
 * @param <V> third type
 */
public class Triplet<T, U, V> {
    private final T first;
    private final U second;
    private final V third;

    /**
     * constructor for initializing object
     *
     * @param first  T object class
     * @param second U object class
     * @param third  V object class
     */
    public Triplet(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * get the first type object
     *
     * @return first type object
     */
    public T getFirst() {
        return first;
    }

    /**
     * get the second type object
     *
     * @return first type object
     */
    public U getSecond() {
        return second;
    }


    /**
     * get the third type object
     *
     * @return first type object
     */
    public V getThird() {
        return third;
    }

}