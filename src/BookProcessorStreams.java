import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookProcessorStreams implements BookProcessor {

    private final List<Book> books;

    public BookProcessorStreams(List<Book> books) {
        this.books = books;
    }

    @Override
    public long count() {
        return books.stream().count();
    }

    @Override
    public List<Book> filter(Predicate<Book> predicate) {
        return books.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
    }

    @Override
    public Book max(Comparator<Book> comparator) {
        return books.stream().max(comparator).orElseThrow();
    }

    @Override
    public Book min(Comparator<Book> comparator) {
        return books.stream().min(comparator).orElseThrow();
    }

    @Override
    public double mean(Function<Book, Long> function) {
        return books.stream()
                    .mapToLong(function::apply)
                    .average()
                    .orElseThrow();
    }

    @Override
    public long countDistinctStrings(Function<Book, String> function) {
        return books.stream()
                    .map(function)
                    .distinct()
                    .count();
    }

    @Override
    public void forEach(Consumer<Book> consumer) {
        books.stream().forEach(consumer);
    }
}
