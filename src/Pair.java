public class Pair<T extends Comparable<T>> implements MinMaxable<T> {
    private T first;
    private T second;

    public class Stats {
        private T minimum;
        private T maximum;
        private boolean equal;

        public Stats() {
            if (first.compareTo(second) <= 0) {
                minimum = first;
                maximum = second;
            } else {
                minimum = second;
                maximum = first;
            }
            equal = (first.compareTo(second) == 0);
        }

        public T getMinimum() {
            return minimum;
        }

        public T getMaximum() {
            return maximum;
        }

        public boolean isEqual() {
            return equal;
        }
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public void swap() {
        T tmp = first;
        first = second;
        second = tmp;
    }

    @Override
    public T min() {
        return (first.compareTo(second) <= 0) ? first : second;
    }

    @Override
    public T max() {
        return (first.compareTo(second) >= 0) ? first : second;
    }

    @Override
    public boolean isEqual() {
        return first.compareTo(second) == 0;
    }

    public Stats getStats() {
        return new Stats();
    }

    @Override
    public String toString() {
        return "Pair[first=" + first + ", second=" + second + "]";
    }
}
