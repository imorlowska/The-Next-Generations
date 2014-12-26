package org.generations.other;

/**
 * Pair implementation.
 * @author Izabela orłowska(imorlowska@gmail.com)
 * @param <T> type of the first object in the pair
 * @param <P> type of the second object in the pair
 */
public class Pair<T, P> {
    private final T first;
    private final P second;
    
    public Pair(T first, P second) {
        this.first = first;
        this.second = second;
    } 
    
    public T getFirst() {
        return first;
    }
    
    public P getSecond() {
        return second;
    }
}
