package com.gulley.dustin.utilities;

/**
 * Holds a pair
 * @param <A>
 * @param <B>
 */
public class Pair<A, B> {

    private A first;    //First element
    private B second;   //Second element

    /**
     * First Setter
     * @param first
     */
    public void setFirst(A first) {
        this.first = first;
    }

    /**
     * Second Setter
     * @param second
     */
    public void setSecond(B second) {
        this.second = second;
    }

    /**
     * First Getter
     * @return
     */
    public A getFirst() {
        return this.first;
    }

    /**
     * Second Getter
     * @return
     */
    public B getSecond() {
        return this.second;
    }

    /**
     * No arg constructor
     */
    public Pair() {}

    /**
     * Arged constructor
     * @param first
     * @param second
     */
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

}
