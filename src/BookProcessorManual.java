import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class BookProcessorManual implements BookProcessor {

    private final List<Book> books;

    public BookProcessorManual(List<Book> books) {
        this.books = books;
    }

    @Override
    public long count() {
        return books.size();
    }

    @Override
    public List<Book> filter(Predicate<Book> predicate) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (predicate.test(b)) {
                result.add(b);
            }
        }
        return result;
    }

    @Override
    public Book max(Comparator<Book> comparator) {
        Book current = books.get(0);
        for (int i = 1; i < books.size(); i++) {
            if (comparator.compare(books.get(i), current) > 0) {
                current = books.get(i);
            }
        }
        return current;
    }

    @Override
    public Book min(Comparator<Book> comparator) {
        Book current = books.get(0);
        for (int i = 1; i < books.size(); i++) {
            if (comparator.compare(books.get(i), current) < 0) {
                current = books.get(i);
            }
        }
        return current;
    }

    @Override
    public double mean(Function<Book, Long> function) {
        long sum = 0;
        for (Book b : books) {
            sum += function.apply(b);
        }
        return (double) sum / books.size();
    }

    @Override
    public long countDistinctStrings(Function<Book, String> function) {
        Set<String> seen = new HashSet<>();
        for (Book b : books) {
            seen.add(function.apply(b));
        }
        return seen.size();
    }

    @Override
    public void forEach(Consumer<Book> consumer) {
        for (Book b : books) {
            consumer.accept(b);
        }
    }
}
