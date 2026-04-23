public interface MinMaxable<T extends Comparable<T>> {
    T min();
    T max();
    boolean isEqual();
}
